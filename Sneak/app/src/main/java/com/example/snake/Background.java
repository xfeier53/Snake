package com.example.snake;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Background extends MainActivity{

                Resources resources = this.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                int height = dm.heightPixels;
                int  width = dm.widthPixels;
                private int offset =90;
                private int Num_square_row = 30;
                private int lineLength;
                private int gridWidth;

                public Background(){
                    lineLength = width - offset*2;
                    gridWidth = lineLength/Num_square_row;
                }
                public int getOffset(){
                    return offset;
                }
                public void setOffset(int offset){
                    this.offset = offset;
                }
                public int getGridSize(){
                    return Num_square_row;
                }
                public void setGridSize(int gridSize){
                    this.Num_square_row = gridSize;
                }
                public int getLineLength(){
                    return lineLength;
                }
                public int getGridWidth(){
                    return gridWidth;
                }
                public void setGridWidth(int gridWidth){
                    this.gridWidth = gridWidth;
                }

}
