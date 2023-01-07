package wia2007.project.tablebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.invitable.R;

import wia2007.project.tablebooking.entity.Menu;

import java.util.List;

public class preOrderFoodSelectAdapter extends RecyclerView.Adapter<preOrderFoodSelectAdapter.preOrderFoodSelectViewHolder>{

    private final LayoutInflater layoutInflater;
    private List<Menu> Menus;

    public preOrderFoodSelectAdapter(Context context, List<Menu> menuList) {
        layoutInflater = LayoutInflater.from(context);
        Menus = menuList;
    }

    @NonNull
    @Override
    public preOrderFoodSelectAdapter.preOrderFoodSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.preorder_foodselect_recyclerview, parent, false);
        preOrderFoodSelectViewHolder viewHolder = new preOrderFoodSelectViewHolder(itemView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull preOrderFoodSelectAdapter.preOrderFoodSelectViewHolder holder, int position) {
        String PriceFormat = "RM";
        PriceFormat += Menus.get(position).getPrice().intValue();

        holder.MenuName.setText(Menus.get(position).getMenu_name());
        holder.Price.setText(PriceFormat);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class preOrderFoodSelectViewHolder extends RecyclerView.ViewHolder {

        TextView MenuName, Price, FoodAmount;
        Button IncrementValue, DecrementValue;

        public preOrderFoodSelectViewHolder(@NonNull View itemView) {
            super(itemView);

            MenuName = itemView.findViewById(R.id.preorder_food_foodName);
            IncrementValue = itemView.findViewById(R.id.preorder_food_increment);
            DecrementValue = itemView.findViewById(R.id.preorder_food_decrementButton);
            Price = itemView.findViewById(R.id.preorder_food_price);
            FoodAmount = itemView.findViewById(R.id.preorder_food_amount);
        }
    }
}
