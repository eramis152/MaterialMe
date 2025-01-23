package com.example.android.materialme;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager based on orientation.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Two columns in horizontal mode.
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Single column in vertical mode.
        }

        // Initialize the ArrayList that will contain the data.
        mSportsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();

        // Create the ItemTouchHelper callback.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                shouldEnableSwipe() ? (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) : 0) { // Disable swipe in landscape.

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // Get the original and target positions
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                // Swap the items in the dataset
                Collections.swap(mSportsData, fromPosition, toPosition);

                // Notify the adapter that the item has moved
                mAdapter.notifyItemMoved(fromPosition, toPosition);

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (shouldEnableSwipe()) {
                    // Get the position of the swiped item
                    int position = viewHolder.getAdapterPosition();

                    // Remove the item from the data set
                    mSportsData.remove(position);

                    // Notify the adapter that the item has been removed
                    mAdapter.notifyItemRemoved(position);
                }
            }
        });

        // Attach the ItemTouchHelper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Determine if swipe gestures should be enabled based on screen orientation.
     */
    private boolean shouldEnableSwipe() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);

        // Obtain the TypedArray of image resources.
        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);

        // Clear the existing data (to avoid duplication).
        mSportsData.clear();

        // Create the ArrayList of Sports objects with titles, info, and images.
        for (int i = 0; i < sportsList.length; i++) {
            // Add each sport with the title, info, and corresponding image resource
            mSportsData.add(new Sport(sportsList[i], sportsInfo[i], sportsImageResources.getResourceId(i, 0)));
        }

        // Recycle the TypedArray to free up memory.
        sportsImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        // Call initializeData to reset the data
        initializeData();

        // Optional: Show a toast to confirm the action
        Toast.makeText(this, "Sports data has been reset", Toast.LENGTH_SHORT).show();
    }
}
