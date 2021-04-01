package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.DataBase;
import com.example.notes.R;

public class NoteAdapter
        extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private DataBase dataSource;
    private OnItemClickListener itemClickListener;
    private final Fragment fragment;
    private int mLastSelectedPosition = -1;

    // Передаём в конструктор источник данных
    public NoteAdapter(DataBase dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

   public int getLastSelectedPosition() {
        return mLastSelectedPosition;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_item_list_notes, viewGroup, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int i) {
        // Вынести на экран, используя ViewHolder
        viewHolder.getNameView().setText(dataSource.getItemAt(i).getNameNote());
    }

    @Override
    public int getItemCount() {
        return dataSource.getItemsCount();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на
    // один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView;

            registerContextMenu(itemView);
            // Обработчик нажатий на этом ViewHolder
            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                nameView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mLastSelectedPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public TextView getNameView() {
            return nameView;
        }
    }

}
