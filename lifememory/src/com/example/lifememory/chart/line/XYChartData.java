package com.example.lifememory.chart.line;

import java.util.ArrayList;

public class XYChartData {
  private String lineName;    //�ߵ����
  private int paintColor;      //�ߵ���ɫ
  public String[] coordinateX;  //x���ֵ
  public int[] coordinateY;    //y���ֵ
  public float[] coodrinateY_double; //y����긡����
  
  public XYChartData() {}
  
  public XYChartData(String lineName, String[] coordinateX, int[] coordinateY, int paintColor){
    this.lineName = lineName;
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
    this.paintColor = paintColor;
  }

  public XYChartData(String lineName, String[] coordinateX, float[] coordinateY_double, int paintColor){
    this.lineName = lineName;
    this.coordinateX = coordinateX;
    this.coodrinateY_double = coordinateY_double;
    this.paintColor = paintColor;
  }
  
  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public String[] getCoordinateX() {
    return coordinateX;
  }

  public void setCoordinateX(String[] coordinateX) {
    this.coordinateX = coordinateX;
  }

  public int[] getCoordinateY() {
    return coordinateY;
  }

  public void setCoordinateY(int[] coordinateY) {
    this.coordinateY = coordinateY;
  }

  public int getPaintColor() {
    return paintColor;
  }

  public void setPaintColor(int paintColor) {
    this.paintColor = paintColor;
  }

  public float[] getCoodrinateY_double() {
    return coodrinateY_double;
  }

  public void setCoodrinateY_double(float[] coodrinateY_double) {
    this.coodrinateY_double = coodrinateY_double;
  }

  
  
  
}