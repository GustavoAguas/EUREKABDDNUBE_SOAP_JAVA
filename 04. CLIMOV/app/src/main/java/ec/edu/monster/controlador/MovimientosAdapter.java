package ec.edu.monster.controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ec.edu.monster.R;
import ec.edu.monster.modelo.Movimiento;

public class MovimientosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Movimiento> movimientos;

    public MovimientosAdapter(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movimiento, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            Movimiento movimiento = movimientos.get(position - 1); // Restamos 1 porque el encabezado ocupa la posición 0
            ((ItemViewHolder) holder).bind(movimiento);
        }
    }

    @Override
    public int getItemCount() {
        return movimientos.size() + 1;
    }

    // ViewHolder para el encabezado
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // ViewHolder para los datos
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCuenta, tvNroMov, tvFecha, tvTipo, tvAccion, tvImporte;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCuenta = itemView.findViewById(R.id.tvCuenta);
            tvNroMov = itemView.findViewById(R.id.tvNroMov);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvAccion = itemView.findViewById(R.id.tvAccion);
            tvImporte = itemView.findViewById(R.id.tvImporte);
        }

        public void bind(Movimiento movimiento) {
            tvCuenta.setText(String.valueOf(movimiento.getCuenta()));
            tvNroMov.setText(String.valueOf(movimiento.getNroMov()));
            tvFecha.setText(movimiento.getFecha());
            tvTipo.setText(movimiento.getTipo());
            tvAccion.setText(movimiento.getAccion());
            tvImporte.setText(String.format("%.2f", movimiento.getImporte()));
        }
    }
}

