package mincor.com.jsonapptest.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindString;
import butterknife.BindView;
import mincor.com.jsonapptest.R;
import mincor.com.jsonapptest.consts.Constants;
import mincor.com.jsonapptest.controllers.base.BaseBackButtonController;
import mincor.com.jsonapptest.mics.BundleBuilder;
import mincor.com.jsonapptest.models.PostData;
import mincor.com.jsonapptest.utils.Support;

/**
 * Created by alexander on 25.08.17.
 */

public class DescController extends BaseBackButtonController {

    @BindString(R.string.title_desc) String title_desc;

    @BindView(R.id.postImgDesc)    ImageView    postImgDesc;
    @BindView(R.id.postNameDesc)   TextView     postNameDesc;
    @BindView(R.id.postDescDesc)   TextView     postDescDesc;
    @BindView(R.id.postContext)    TextView     postContext;
    @BindView(R.id.postDateDesc)   TextView     postDateDesc;

    public DescController(PostData postData){
        this(new BundleBuilder(new Bundle())
                .putParcelable(Constants.DESC_BUNDLE_KEY, postData)
                .build());
    }

    public DescController(Bundle args) {
        super(args);
    }

    private PostData postData = getArgs().getParcelable(Constants.DESC_BUNDLE_KEY);

    @Override protected String getTitle() {
        return title_desc;
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_description, container, false);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        String imgUrl = postData.getJsonContent(Constants.CONTENT_IMAGE);
        if(TextUtils.isEmpty(imgUrl)) {
            Glide.with(this.getActivity()).load(R.drawable.default_avatar).centerCrop().into(postImgDesc);
        } else {
            Glide.with(this.getActivity()).load(imgUrl).centerCrop().dontAnimate().into(postImgDesc);
        }

        postNameDesc.setText(postData.getRubric());
        postDateDesc.setText(Support.getBetweenTime(postData.getDate(), Constants.DATE_PATTERN));
        postDescDesc.setText(postData.getTitle());
        postContext.setText(postData.getJsonContent(Constants.CONTENT_TEXT));

    }

    @Override
    protected void goBack() {
        this.getRouter().popToRoot();
    }
}
