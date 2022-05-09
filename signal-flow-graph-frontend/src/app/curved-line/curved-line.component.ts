import { Component, Injectable, OnInit } from '@angular/core';
import { fabric } from 'fabric';
import { Line } from 'fabric/fabric-impl';

@Injectable({
  providedIn : 'root'
})
export class CurvedLineComponent {

  
top = 200;
left = 200;
  generate(indeces: any, gain:any){
    var x1 = indeces[0] + 30;
    var x2 = indeces[2] + 30; 
    var y1 = indeces[1];
    var y2 = x1 < x2 ?indeces[3] - 10: indeces[3] + 10;
    var height = (y1 + y2) / 2;
    if(x1 < x2){
      
      height = y1 >= y2? y1 - 300 : y2 - 300;
    }else{
      y1 += 60;
      y2 += 60;
      height = y1 <= y2? y1 + 300 : y2 + 300;
    }

    console.log("y1 is: "+ y1 + " and y2 is: " + y2 + " and the last thing, height is: " + height);
    var curved_line = new fabric.Path('M'+String(x1)+' '+ String(y1) +'Q' + String((x1 + x2) / 2) + ' ' + String(height) + ' ' + String(x2) + ' ' + String(y2),{
      fill: '', 
      stroke: 'black', 
      objectCaching: false 
    });

    curved_line.selectable = true
    console.log(curved_line)

    var triangle = new fabric.Triangle({
      width: 10, 
      height: 15, 
      fill: 'black', 
      left: x1 < x2 ? indeces[2] + 35: indeces[2] + 25,
      top: x1 < x2 ? indeces[3] : indeces[3] + 60, 
      angle: indeces[0] > indeces[2] ?  0 : 180
  });

  var rad = Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2)) / 2;
  console.log("rad = " + rad);
  var text_top = 0;
  var text_left = Math.abs(indeces[0] + indeces[2]) / 2; 
  if(x1 < x2 && Math.abs(y1 - y2) < 100){
    text_top = (y1 + y2) / 2 - rad;      
  }
  else if (x1 > x2 && Math.abs(y1 - y2) < 100){
    text_top = (y1 + y2) / 2 + rad;
  }
  else{
      if(x1 < x2){
        text_left = x1 + 50;
        text_top = y1 -50;
      } 
      else{
          text_left = x1 - 50;
          text_top = y1 + 50;

      }
  }
  console.log("y1 = " + y1 + " y2 = " + y2 + "and the middle point at" + ((y1 + y2) /2) + "and the height = " + text_top);
  var text = new fabric.Text((String(gain)),{
    fill : 'black',
    left :  text_left,
    top :   x1 < x2 ? text_top + 50 : text_top - 30
})
    var objs = [curved_line, triangle, text];
    var arrow = new fabric.Group(objs);
    return arrow;
  }

}

  


