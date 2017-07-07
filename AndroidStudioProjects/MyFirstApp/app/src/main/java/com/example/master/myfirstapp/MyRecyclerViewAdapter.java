package com.example.master.myfirstapp;

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
    import android.view.ViewTreeObserver;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.squareup.picasso.MemoryPolicy;
    import com.squareup.picasso.NetworkPolicy;
    import com.squareup.picasso.Picasso;

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

            if(! String.valueOf(mDataset.get(position).getName()).matches(".*[^a-z].*")){
                holder.tv.setText("Name unknown");
            }else{
                holder.tv.setText(String.valueOf(mDataset.get(position).getName()));
            }
            if((mDataset.get(position).getGender() + mDataset.get(position).getBreed()).length()<1){

                final Picasso.Builder builder = new Picasso.Builder(mContext);
                builder.listener(new Picasso.Listener()
                {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                    {

                        Picasso
                                .with(mContext)
                                .load("http://www.james-odonnell.com/LostPets/images/markerLargeNIF.png")
                                .resize(100, 50)
                                .into(holder.iv);
                        //Picasso.with(mContext).load("http://www.james-odonnell.com/LostPets/images/markerLargeNIF.png").networkPolicy(NetworkPolicy.NO_CACHE).into(holder.iv);
                    }
                });
                builder.build()
                        .load((mDataset.get(position).getImageAddress()).replaceAll("\\s+",""))
                        .resize(100, 50)
                        .into(holder.iv);

            }else{
                holder.tv2.setText(mDataset.get(position).getGender() + mDataset.get(position).getBreed());
            }

            /*
            holder.tv.setText(String.valueOf(mDataset.get(position).getName()));
            holder.tv2.setText(mDataset.get(position).getGender() + mDataset.get(position).getBreed());
            */

            System.out.println("testprints 123333");
            System.out.println(mDataset.get(position).getImageAddress());



            Picasso.Builder builder = new Picasso.Builder(mContext);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    Picasso.with(mContext).load("http://www.james-odonnell.com/LostPets/images/markerLargeNIF.png").skipMemoryCache().into(holder.iv);
                }
            });
            builder.build().load((mDataset.get(position).getImageAddress()).replaceAll("\\s+","")).into(holder.iv);



            //OLD WORKING LINE
            //Picasso.with(mContext).load((mDataset.get(position).getImageAddress()).replaceAll("\\s+","")).into(holder.iv);


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

