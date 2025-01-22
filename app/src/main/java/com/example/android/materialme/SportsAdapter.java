package com.example.android.materialme;

import android.content.Context;
import android.content.Intent;  // Import for Intent
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<Sport> mSportsData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param sportsData ArrayList containing the sports data.
     * @param context Context of the application.
     */
    SportsAdapter(Context context, ArrayList<Sport> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public SportsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(SportsAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
        Sport currentSport = mSportsData.get(position);

        // Populate the textviews and imageview with data.
        holder.bindTo(currentSport);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member variables for the TextViews and ImageView.
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mSportImage = itemView.findViewById(R.id.sportsImage); // Initialize ImageView

            // Set OnClickListener to the itemView
            itemView.setOnClickListener(this);  // Set the click listener
        }

        /**
         * Method to bind the data to the views.
         *
         * @param currentSport The sport object with the data.
         */
        void bindTo(Sport currentSport) {
            // Set the text for the TextViews
            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());

            // Set the image for the ImageView
            mSportImage.setImageResource(currentSport.getImageResource());
        }

        /**
         * onClick method triggered when any item is clicked.
         * Launches the DetailActivity with the clicked sport data.
         */
        @Override
        public void onClick(View v) {
            // Get the clicked sport object
            Sport clickedSport = mSportsData.get(getAdapterPosition());

            // Create an intent to start the DetailActivity
            Intent intent = new Intent(mContext, DetailActivity.class);

            // Pass the sport data to the DetailActivity
            intent.putExtra("sport_title", clickedSport.getTitle());
            intent.putExtra("sport_info", clickedSport.getInfo());
            intent.putExtra("sport_image", clickedSport.getImageResource());

            // Start the DetailActivity
            mContext.startActivity(intent);
        }
    }
}
