import { Component, Injectable, OnInit } from '@angular/core';
import { fabric } from 'fabric';
import { Line } from 'fabric/fabric-impl';

@Injectable({
  providedIn : 'root'
})
export class LineComponent {

  

  //generate the arrow 
  generate(indeces: any, angle : any, gain:any){
    //set the line to the left of the vertex
    if(indeces[0] < indeces[2]){
      indeces[0] += 60; 
    }
    else{
      indeces[2] += 60; 
    }

    indeces[1] += 30;
    indeces[3] += 30;

    var angle_in_radian = Math.atan((indeces[3]-indeces[1])/(indeces[2]-indeces[0]))
    var angle_in_degree = angle_in_radian * 180 / Math.PI; //get the angle in degree

    //option of the text above the line 
    var text_height : number = Math.abs(indeces[3] - indeces[1]); 
    var text_align : number = Math.abs(indeces[2] - indeces[0])

    //get the correct width for the elements
    if(indeces[0] > indeces[2]){ 
      text_align = indeces[0] - text_align / 2;  //case : the direction from right to left
    }
    else{ 
      text_align = indeces[0] + text_align / 2;  //case : the direction from left to right
    }

    //get the correct height for the elements
    if(indeces[1] >= indeces[3]){ 
      text_height = indeces[1] - text_height / 2; //case : the direction from down to up
    }
    else{  
      text_height = indeces[1] + text_height / 2; //case: the direction from up to down
    }


    var line = new fabric.Line( [indeces[0], indeces[1], indeces[2], indeces[3]] ,{
      stroke:"black",
      fill:"green",
      strokeWidth:1,
      selectable:true, 
    })
    console.log(line)
    var tri_height = text_height;
    var tri_align = text_align;

    if(indeces[0] > indeces[2] && indeces[1] > indeces[3]){
        tri_height += 5;
        tri_align -= 5;
    }
    else if(indeces[0] > indeces[2] && indeces[1] < indeces[3]){
      tri_height += 5;
      tri_align += 5;
    }
    else if(indeces[0] < indeces[2] && indeces[1] > indeces[3]){
      tri_height -= 5;
      tri_align -= 3;
    }
    else{
      tri_height -= 5;
      tri_align += 3;
    }
    var triangle = new fabric.Triangle({
      width: 10, 
      height: 15, 
      fill: 'black', 
      left: tri_align, 
      top: tri_height ,
      angle: indeces[0] > indeces[2] ? angle_in_degree - 90 : angle_in_degree + 90
  });



    var tex_height = text_height;
    var tex_align = text_align;

    if(indeces[0] > indeces[2] && indeces[1] > indeces[3]){
        tex_height -= 40;
        tex_align += 10;
    }
    else if(indeces[0] > indeces[2] && indeces[1] < indeces[3]){
      tex_height -= 40;
      tex_align -= 30;
    }
    else if(indeces[0] < indeces[2] && indeces[1] > indeces[3]){
      tex_height -= 40;
      tex_align -= 30;
    }
    else{
      tex_height -= 40;
      tex_align += 20;
    }
    //add the text
    var text = new fabric.Text((String(gain)),{
     fill : 'black',
      top : tex_height,
      left : tex_align,
    })

    //save all objects in array
    var objs = [line, triangle, text];
    var arrow = new fabric.Group(objs); //group the objects together
    //console.log("the angle is: "+ angle);
    //arrow.set("angle", angle)
    return arrow;
  }
  

}
