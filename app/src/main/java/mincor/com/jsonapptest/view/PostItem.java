package mincor.com.jsonapptest.view;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mincor.com.jsonapptest.R;
import mincor.com.jsonapptest.consts.Constants;
import mincor.com.jsonapptest.models.PostData;
import mincor.com.jsonapptest.utils.Support;

/**
 * Created by alexander on 25.08.17.
 */

public class PostItem extends AbstractItem<PostItem, PostItem.ViewHolder> {

    @Override    public int getType()       {        return R.id.item_post_id;       }
    @Override    public int getLayoutRes()  {        return R.layout.item_post;      }

    PostData postData;

    public PostItem withPostData(PostData pd){
        this.postData = pd;
        return this;
    }

    @Override public void bindView(PostItem.ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.postName.setText(postData.getRubric());

        String title = postData.getTitle();
        if(title.length() > Constants.MAX_DESC_LENGTH){
            title = title.substring(0, Constants.MAX_DESC_LENGTH)+"...";
        }
        viewHolder.postDesc.setText(title);
        viewHolder.postTime.setText(Support.getBetweenTime(postData.getDate(), Constants.DATE_PATTERN));

        String imgUrl = postData.getJsonContent(Constants.CONTENT_IMAGE);
        if(TextUtils.isEmpty(imgUrl)) {
            Glide.with(viewHolder.itemView.getContext()).load(R.drawable.default_avatar).centerCrop().into(viewHolder.circleImageView);
        } else {
            Glide.with(viewHolder.itemView.getContext()).load(imgUrl).centerCrop().dontAnimate().into(viewHolder.circleImageView);
        }

    }
    @Override public void unbindView(PostItem.ViewHolder holder) {
        super.unbindView(holder);
        Glide.clear(holder.circleImageView);
        holder.circleImageView.setImageResource(0);
        holder.circleImageView.setImageDrawable(null);
        holder.circleImageView.setImageBitmap(null);
        holder.postName.setText(null);
        holder.postDesc.setText(null);
        holder.postTime.setText(null);
    }

    @Override public PostItem.ViewHolder getViewHolder(View parent) {
        return new PostItem.ViewHolder(parent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.circleImageView)       ImageView         circleImageView;
        @BindView(R.id.postName)              TextView          postName;
        @BindView(R.id.postDesc)              TextView          postDesc;
        @BindView(R.id.postTime)              TextView          postTime;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
