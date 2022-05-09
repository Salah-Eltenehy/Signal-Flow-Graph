import { Component, Injectable, OnInit } from '@angular/core';
import { fabric } from 'fabric';

@Injectable({
  providedIn : 'root'
})
export class LoopComponent {

  
i = 1;
top = 200;
left = 200;
x = 0
y = 5
  generate(left : any, top: any, gain:any){
    var loop = new fabric.Circle({
      name : 'L' + this.i,
      radius:30+this.x,
      stroke:"black",
      fill:"white",
      strokeWidth:1,
      selectable:true,
      scaleX:1,
      scaleY:1,
      top: top-60,
      left : left
    })

  
    var text = new fabric.Text((String(gain)),{
      fill : 'black',
      top : top-100,
      left : left+20
  })

  /*
  var ret_arr = [machine, text]

  this.i += 1;
  if(this.i>9){
    this.x=12
    this.y = 15
  }
*/

  console.log(loop)
    var triangle = new fabric.Triangle({
      width: 10, 
      height: 15, 
      fill: 'black', 
      left: left+30, 
      top: top-5,
      angle: 110 
  });
    var objs = [loop, triangle, text];
    var loop_arrow = new fabric.Group(objs);
        //canvas.add(alltogetherObj);
    //loop_arrow.set("angle", 180)
    return loop_arrow;
  }

}
