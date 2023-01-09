package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import wia2007.project.tablebooking.entity.BookingContainMenu;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<BookingContainMenu> BCM;
    private List<String> NameList;
    private List<Integer> PriceList;


    public FoodListAdapter(Context context, List<BookingContainMenu> BCM, List<String> NameList, List<Integer> PriceList) {
        layoutInflater = LayoutInflater.from(context);
        this.BCM = BCM;
        this.NameList = NameList;
        this.PriceList = PriceList;
    }

    @NonNull
    @Override
    public FoodListAdapter.FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.foodlist_recyclerview, parent, false);
        FoodListViewHolder viewHolder = new FoodListViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.FoodListViewHolder holder, int position) {

        holder.FoodName.setText(NameList.get(position));
        holder.Price.setText(PriceList.get(position).toString());
        holder.Quantity.setText(BCM.get(position).getQuantity());
    }

    @Override
    public int getItemCount() { return BCM.size(); }

    public static class FoodListViewHolder extends RecyclerView.ViewHolder {

        TextView Quantity, FoodName, Price;

        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);

            Quantity = itemView.findViewById(R.id.foodList_quantity);
            FoodName = itemView.findViewById(R.id.foodList_foodName);
            Price = itemView.findViewById(R.id.foodList_price);
        }
    }
}