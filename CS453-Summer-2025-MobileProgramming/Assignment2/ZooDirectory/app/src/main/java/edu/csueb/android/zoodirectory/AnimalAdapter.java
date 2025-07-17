package edu.csueb.android.zoodirectory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private Context context;
    private List<Animal> animals;

    public AnimalAdapter(Context context, List<Animal> animals) {
        this.context = context;
        this.animals = animals;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animals.get(position);
        holder.nameTextView.setText(animal.getName());
        holder.imageView.setImageResource(animal.getThumbnailId());

        holder.itemView.setOnClickListener(v -> {
            if (position == animals.size() - 1) {
                new AlertDialog.Builder(context)
                        .setTitle("Warning")
                        .setMessage("This animal is very scary. Proceed?")
                        .setPositiveButton("Yes", (dialog, which) -> openDetail(animal))
                        .setNegativeButton("No", null)
                        .show();
            } else {
                openDetail(animal);
            }
        });
    }

    private void openDetail(Animal animal) {
        Intent intent = new Intent(context, AnimalDetailActivity.class);
        intent.putExtra("name", animal.getName());
        intent.putExtra("description", animal.getDescription());
        intent.putExtra("imageId", animal.getImageId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}