package com.example.tiswamemp.BottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.example.tiswamemp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChangePasswordBottomSheet extends BottomSheetDialogFragment {

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      final View v = inflater.inflate(R.layout.change_pass_bottom_sheet, container, false);

      final Context context = v.getContext();

      return v;
   }


}
