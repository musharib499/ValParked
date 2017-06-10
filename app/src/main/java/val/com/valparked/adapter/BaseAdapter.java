package val.com.valparked.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by musharib ali on 22/3/16.
 */
public class BaseAdapter<S, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private static final String TAG = "BaseRecyclerAdapter";
    private Context mContext;
    private List<S> mObjects;
    private BindAdapterListener mListener;
    private T mHolder;
    private Class<T> mHolderClass;
    private int layoutId;

    public BaseAdapter(Context context, List<S> objects, BindAdapterListener listener, Class<T> holderClass, int layoutId) {
        mContext = context;
        mObjects = objects;
        mHolderClass = holderClass;
        mListener = listener;
        this.layoutId = layoutId;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            return mHolderClass.getConstructor(View.class).newInstance(LayoutInflater.from(mContext).inflate(layoutId, parent, false));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        mListener.onBind(holder, position);
    }

    @Override
    public int getItemCount() {
        return mObjects == null ? 0 : mObjects.size();
    }

    public interface BindAdapterListener<T> {
        void onBind(T holder, int position);
    }
}
