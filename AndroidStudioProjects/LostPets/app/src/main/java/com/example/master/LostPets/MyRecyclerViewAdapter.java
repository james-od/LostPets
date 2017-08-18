package com.example.master.LostPets;

/**
 * Created by Master on 08-Oct-16.
 */

    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.support.v7.widget.RecyclerView;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;

    import java.util.ArrayList;

    public class MyRecyclerViewAdapter extends RecyclerView
            .Adapter<MyRecyclerViewAdapter
            .DataObjectHolder> {
        private static String LOG_TAG = "MyRecyclerViewAdapter";
        private ArrayList<GPSTestResultsArr> mDataset;
        private static MyClickListener myClickListener;
        private Context mContext;

        public static class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            TextView tv;
            TextView tv2;
            ImageView iv;


            public DataObjectHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.textView);
                tv2 = (TextView) itemView.findViewById(R.id.textView2);
                iv = (ImageView) itemView.findViewById(R.id.imageView);
                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(getAdapterPosition(), v);
                System.out.println("test MyRecyclerViewAdapter OnClick " + getAdapterPosition());
                System.out.println(getAdapterPosition());
                //System.out.println(mDataset.get(position).getImageAddress());
            }
        }

        public void setOnItemClickListener(MyClickListener myClickListener) {
            this.myClickListener = myClickListener;
        }

        public MyRecyclerViewAdapter(Context context, ArrayList<GPSTestResultsArr> myDataset) {
            mDataset = myDataset;
            this.mContext = context;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_primary_card_view, parent, false);

            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }


        @Override
        public void onBindViewHolder(final DataObjectHolder holder, final int position) {
            System.out.println("Name = "+mDataset.get(position).getName());

            if(! String.valueOf(mDataset.get(position).getName()).matches(".*[^a-z].*") || mDataset.get(position).getName().length() == 0){
                holder.tv.setText("Name unknown");
            }else{
                if (mDataset.get(position).getName().length() > 21){
                    holder.tv.setText(String.valueOf(mDataset.get(position).getName().substring(0,21)) + "..");
                }else{
                    holder.tv.setText(String.valueOf(mDataset.get(position).getName()));
                }
            }
            if((mDataset.get(position).getGender() + mDataset.get(position).getBreed()).length()<1){

            }else{
                if(mDataset.get(position).getBreed().length() > 30){
                    holder.tv2.setText(mDataset.get(position).getGender() + mDataset.get(position).getBreed().substring(0,30)+"..");
                }else{
                    holder.tv2.setText(mDataset.get(position).getGender() + mDataset.get(position).getBreed());
                }
            }

            /*
            holder.tv.setText(String.valueOf(mDataset.get(position).getName()));
            holder.tv2.setText(mDataset.get(position).getGender() + mDataset.get(position).getBreed());
            */

            System.out.println("testprints 123333");
            System.out.println("get item count "+getItemCount());
            System.out.println(mDataset.get(position).getImageAddress());

            String url = (mDataset.get(position).getImageAddress()).replaceAll("\\s+","");
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.marker_large_nif)
                    .centerCrop()
                    .fitCenter()
                    .into(holder.iv);


            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    System.out.println("testicles 123333");
                    //Toast.makeText(v.getContext(), "http://www.doglost.co.uk/dog-blog.php?dogId=" + mDataset.get(position).getId(), Toast.LENGTH_SHORT).show();

                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("testicles 123333");
                    String url = "http://www.doglost.co.uk/dog-blog.php?dogId=" + mDataset.get(position).getId();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    v.getContext().startActivity(i);
                    //Toast.makeText(v.getContext(), "Short click" + mDataset.get(position).getImageAddress(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void addItem(GPSTestResultsArr dataObj, int index) {
            mDataset.add(index, dataObj);
            notifyItemInserted(index);
        }

        public void deleteItem(int index) {
            mDataset.remove(index);
            notifyItemRemoved(index);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public interface MyClickListener {
            public void onItemClick(int position, View v);
        }
    }

