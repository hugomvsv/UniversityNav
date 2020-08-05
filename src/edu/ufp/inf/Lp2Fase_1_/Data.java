package edu.ufp.inf.Lp2Fase_1_;


import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Data implements Comparable<Data> , Serializable {

  //Atributos
  private Integer dia_semana;
  private Integer hora;
  private Integer minuto;

  //Constructor
  // Cria uma data com a data atual
  public Data() { //dá as horas atuais
    GregorianCalendar gc = new GregorianCalendar();
    this.dia_semana = gc.get(Calendar.DAY_OF_WEEK);
    this.hora = gc.get(Calendar.HOUR_OF_DAY);
    this.minuto=gc.get(Calendar.MINUTE);
  }

  //Constructor 2 hora atual
  //constroi uma hora predefenida
  public Data(Integer dia_semana, Integer hora, Integer minuto) {
    this.dia_semana = dia_semana;
    this.hora = hora;
    this.minuto = minuto;
  }

  //método compareTo que irá comparar duas datas!
  @Override
  public int compareTo(Data o)
  {
    if(this.dia_semana>o.getDia_semana())
      return 1;
    if(this.dia_semana<o.getDia_semana())
      return -1;
    if(this.hora>o.getHora())
      return 1;
    if(this.hora<o.getHora())
      return -1;
   if(Integer.compare(this.dia_semana,o.getDia_semana())==0 &&
      Integer.compare(this.hora,o.getHora())==0 && Integer.compare(this.minuto,o.getMinuto())==0)
     return 0;
    return Integer.compare(this.minuto,o.getMinuto());
  }

  @Override
  public String toString() {
    return  ""+dia_semana +'/'+ hora +'/' + minuto;
  }

  public String paraString()
  {
    return  "dia: "+dia_semana +" hora: "+ hora +" minuto: " + minuto;
  }

  //gets and sets
  public Integer getDia_semana() {
    return dia_semana;
  }

  public void setDia_semana(Integer dia_semana) {
    this.dia_semana = dia_semana;
  }

  public Integer getHora() {
    return hora;
  }

  public void setHora(Integer hora) {
    this.hora = hora;
  }

  public Integer getMinuto() {
    return minuto;
  }

  public void setMinuto(Integer minuto) {
    this.minuto = minuto;
  }
}