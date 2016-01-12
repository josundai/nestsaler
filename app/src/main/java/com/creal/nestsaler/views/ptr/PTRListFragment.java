package com.creal.nestsaler.views.ptr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.creal.nestsaler.R;
import com.creal.nestsaler.actions.AbstractAction;
import com.creal.nestsaler.actions.PaginationAction;
import com.creal.nestsaler.actions.Pagination;
import com.creal.nestsaler.util.ErrorAdapter;
import com.creal.nestsaler.views.HeaderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

public abstract class PTRListFragment<Result> extends ListFragment implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "XYK-PTRListFragment";

    private LoadingSupportPTRListView mLoadingSupportPTRListView;
    private ContentListAdapter mPtrListAdapter;
    private PaginationAction<Result> mAction;
    private HeaderView mHeaderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_simple_ptr_list, null);
        mHeaderView = (HeaderView) view.findViewById(R.id.header);
        mHeaderView.hideRightImage();
        if(getTitleResId() > 0)
            mHeaderView.setTitle(getTitleResId());
        mLoadingSupportPTRListView = (LoadingSupportPTRListView)view.findViewById(R.id.refresh_widget);
        mLoadingSupportPTRListView.setOnRefreshListener(this);
        mLoadingSupportPTRListView.setMode(getPTRMode());
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!initOnResume())
            loadFirstPage(true);
    }

    protected HeaderView getHeaderView(){
        return mHeaderView;
    }

    public void onResume(){
        super.onResume();
        if(initOnResume())
            loadFirstPage(true);
    }

    protected LoadingSupportPTRListView getLoadingSupportPTRListView(){
        return mLoadingSupportPTRListView;
    }

    protected boolean initOnResume(){
        return false;
    }

    private void loadFirstPage(boolean isInitialLoad){
        Log.d(TAG, "loadFirstPage");
        if(isInitialLoad)
            mLoadingSupportPTRListView.showLoadingView();
        mAction = getPaginationAction();
        mAction.execute(
            new AbstractAction.UICallBack<Pagination<Result>>() {
                public void onSuccess(Pagination<Result> result) {
                    if(isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                        return;
                    mPtrListAdapter = new ContentListAdapter(getActivity(), result.getItems());
                    setListAdapter(mPtrListAdapter);
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                    onPostLoadFirstPage();
                }

                public void onFailure(AbstractAction.ActionError error) {
                    if(isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                        return;
                    mAction = mAction.cloneCurrentPageAction();
                    getListView().getLayoutParams().height = AbsListView.LayoutParams.MATCH_PARENT;
                    setListAdapter(getErrorAdapter());
                    getListView().setDivider(null);
                    mLoadingSupportPTRListView.showListView();
                    mLoadingSupportPTRListView.refreshComplete();
                }
            }
        );
    }

    private void loadNextPage(){
        Log.d(TAG, "loadNextPage");
        mAction = mAction.getNextPageAction();
        mAction.execute(
                new AbstractAction.UICallBack<Pagination<Result>>() {
                    public void onSuccess(Pagination<Result> result) {
                        if(isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        if(result.getItems().isEmpty()){
                            Toast.makeText(getActivity(), R.string.load_done, Toast.LENGTH_SHORT).show();
                            mAction = mAction.getPreviousPageAction();
                        }else {
                            mPtrListAdapter.addMore(result.getItems());
                        }
                        mLoadingSupportPTRListView.refreshComplete();
                    }

                    public void onFailure(AbstractAction.ActionError error) {
                        if(isDetached() || getActivity() == null) //DO NOT update the view if this fragment is detached from the activity.
                            return;
                        Toast.makeText(getActivity(), getLoadNextPageError(error), Toast.LENGTH_SHORT).show();
                        mAction = mAction.getPreviousPageAction();
                        mLoadingSupportPTRListView.refreshComplete();
                    }
                }
        );
    }

    public String getLoadNextPageError(AbstractAction.ActionError error){
        return getString(R.string.load_failed);
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        if ( getListAdapter() instanceof ErrorAdapter){
            loadFirstPage(true);
            return;
        }

        Result result = (Result)getListView().getItemAtPosition(position);
        showDetailActivity(result);
    }

    protected void onPostLoadFirstPage(){ }

    public PullToRefreshBase.Mode getPTRMode(){
        return PullToRefreshBase.Mode.BOTH;
    }

    public abstract PaginationAction<Result> getPaginationAction();

    public abstract View getListItemView(Result item, View convertView, ViewGroup parent, LayoutInflater inflater);

    public abstract int getTitleResId();

    public void showDetailActivity(Result result){ }

    public ListAdapter getErrorAdapter(){
        return new ErrorAdapter(getActivity());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadFirstPage(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadNextPage();
    }

    public class ContentListAdapter extends PTRListAdapter<Result> {
        private LayoutInflater mInflater;
        public ContentListAdapter(Context context, List<Result> objects) {
            super(context, 0, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final Result itemObject = getItem(position);
            return getListItemView(itemObject, convertView, parent, mInflater);
        }
    }
}