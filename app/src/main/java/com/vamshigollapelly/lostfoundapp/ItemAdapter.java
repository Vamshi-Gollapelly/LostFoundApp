package com.vamshigollapelly.lostfoundapp;

import android.net.Uri;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends
        RecyclerView.Adapter<ItemAdapter.VH> {

    public interface OnClickListener {
        void onClick(LostFoundItem item);
    }

    private final List<LostFoundItem> items;
    private final OnClickListener     listener;

    public ItemAdapter(List<LostFoundItem> items,
                       OnClickListener listener) {
        this.items    = items;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent,
                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        LostFoundItem item = items.get(pos);
        h.tvType.setText(item.getType());
        h.tvDesc.setText(item.getDescription());
        h.tvStamp.setText(item.getTimestamp());
        h.tvCat.setText(item.getCategory());

        // Color badge: red = lost, green = found
        h.tvType.setBackgroundColor("Lost".equals(item.getType())
                ? 0xFFE74C3C : 0xFF27AE60);

        if (item.getImageUri() != null
                && !item.getImageUri().isEmpty())
            h.ivThumb.setImageURI(Uri.parse(item.getImageUri()));
        else
            h.ivThumb.setImageResource(
                    android.R.drawable.ic_menu_gallery);

        h.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvType, tvDesc, tvStamp, tvCat;
        ImageView ivThumb;
        VH(@NonNull View v) {
            super(v);
            tvType  = v.findViewById(R.id.tvType);
            tvDesc  = v.findViewById(R.id.tvDescription);
            tvStamp = v.findViewById(R.id.tvTimestamp);
            tvCat   = v.findViewById(R.id.tvCategory);
            ivThumb = v.findViewById(R.id.ivThumb);
        }
    }
}
