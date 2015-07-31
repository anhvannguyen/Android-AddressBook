package me.anhvannguyen.android.addressbook;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.anhvannguyen.android.addressbook.data.AddressContract;

/**
 * Created by anhvannguyen on 7/29/15.
 */
public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAddressTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mAddressTextView = (TextView) itemView.findViewById(R.id.address_item_name_textview);
        }
    }

    public AddressRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int firstNameIndex = mCursor.getColumnIndex(AddressContract.AddressEntry.COLUMN_FIRST_NAME);
        String firstName = mCursor.getString(firstNameIndex);
        int lastNameIndex = mCursor.getColumnIndex(AddressContract.AddressEntry.COLUMN_LAST_NAME);
        String lastName = mCursor.getString(lastNameIndex);

        holder.mAddressTextView.setText(String.format("%1$s %2$s", firstName, lastName));
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }
}
