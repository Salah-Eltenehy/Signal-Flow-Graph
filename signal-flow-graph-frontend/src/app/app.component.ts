import { Component, OnInit } from '@angular/core';
import { waitForAsync } from '@angular/core/testing';
import { fabric } from 'fabric';
import { delay, last, subscribeOn } from 'rxjs';
import { LineComponent } from './line/line.component';
import { VertexComponent } from './vertex/vertex.component';
import { CurvedLineComponent } from './curved-line/curved-line.component';
import { LoopComponent } from './loop/loop.component';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { FormsModule } from '@angular/forms';

import 'fabric-history';
import { ResultComponent } from './result/result.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'porduce-consumer';


  change_url  = "http://localhost:8080/change";
  map_url  = "http://localhost:8080/map/calculate";
  update_url = "http://localhost:8080/update";
  stop_request = "http://localhost:8080/stop"
  replay_request = "http://localhost:8080/replay"
  item_request = "http://localhost:8080/items"
  new_request ="http://localhost:8080/new"


  gain : any = 1;
  res : string = "" ;
  //res_arr : any;
  last : any;
  replay1 = false;
  all_shapes : any[] = [];
  all_changes = ["Q1", "M1", "red", "3000"]//, ["M1", "Q2", "red", "0"]]
  change : string = "";
  canvas : any;
  tempCanvas : any;
  index_array  = [0,0,0,0];
  name_arr : any[] = [];
  x :any;
  items : number  = 7 ;
  changes = ""
  Stop : boolean = false;
  first_queue = true;
  whole_map : Map<string, Array<string>> = new Map();
  constructor(
    private vertex : VertexComponent,
    private result : ResultComponent,
    private loop : LoopComponent,
    private curved_line : CurvedLineComponent,
    private line : LineComponent,
    private http : HttpClient,
    ){}


  ngOnInit(): void {
    this.canvas = new fabric.Canvas('canvas',{
      selection : true,
      isDrawingMode : false
    })
  }
 
  getRandomInt() {
    return Math.floor(Math.random() * 14) + 7;
  }
   
  generate_vertex(){
    var vertex = this.vertex.generate()
    this.canvas.add(vertex[0]);
    this.canvas.add(vertex[1]);
    this.x = vertex[0]
    this.all_shapes.push(vertex[0]);
    console.log(this.all_shapes)
    var sel = new fabric.ActiveSelection(vertex, {
      canvas: this.canvas,
    });
    this.canvas.setActiveObject(sel);
    this.canvas.getActiveObject().toGroup();
    this.last = this.canvas.getActiveObject();
    this.lock()
    this.canvas.requestRenderAll();

  }

  generate_curved_line(){
    console.log("name is " + this.name_arr[0].substring(0,1))
    var angle = Math.atan((this.index_array[3]-this.index_array[1])/(this.index_array[2]-this.index_array[0]))
    //console.log(angle*180/Math.PI)
    //var length = Math.sqrt(Math.pow(this.index_array[3]-this.index_array[1],2)+Math.pow(this.index_array[2]-this.index_array[0],2))
    console.log(length)
    var curved_line = this.curved_line.generate(this.index_array, this.gain)
    this.canvas.add(curved_line);
    this.last = curved_line;
    this.add_element();
    console.log(this.whole_map);
  }

  generate_loop(){
    this.name_arr[0] = this.name_arr[1]
    var loop = this.loop.generate(this.index_array[2], this.index_array[3], this.gain)
    this.canvas.add(loop);
    console.log(this.all_shapes)
    this.lock()
    this.canvas.requestRenderAll();
    this.add_element()
    console.log(this.whole_map);
  }

  lock(){
    this.canvas.getActiveObject().set("lockScalingX", 'true');
    this.canvas.getActiveObject().set("lockScalingY", 'true');
    this.canvas.getActiveObject().set("lockRotation", 'true');
    this.canvas.renderAll();
  }

  flash(){
    this.white()
     setTimeout(() => {
       this.color("green") 
     }, 200);
  }

  white(){
    this.canvas.getActiveObject().set("fill", 'white');
    this.canvas.renderAll();
  }

  color(s:string){
    this.canvas.getActiveObject().set("fill", s);
    this.canvas.renderAll();
  }
    
  evaluate_tranfer_function(){
    this.Stop = false
    //string this.result;
    //this.http.post(this.item_request, this.items).subscribe();
    this.http.post(this.map_url, Object.fromEntries(this.whole_map),
     {headers : new HttpHeaders, responseType : 'text'}).subscribe((data: any) => {
      this.res = data;
      console.log("result from backend : "+ this.res);
      this.generate_result(this.res);
    });
  }

  generate_result(res : string){
    var res_arr = res.split("#");
    console.log("res arr is "+ res_arr);
    var result = this.result.generate(res_arr);
    this.canvas.add(result);
  }

  apply(x : string[]){
     if (x[0].substring(0,1) == 'Q'){
      let sh;
      for (let j=0; j<this.all_shapes.length; j++){
        let st
        if (Array.isArray(this.all_shapes[j]) && this.all_shapes[j][0].get("name") !== null) st = this.all_shapes[j][0].get("name")
          console.log(typeof(this.all_shapes[j]))
        if (Array.isArray(this.all_shapes[j]) && x[0] == this.all_shapes[j][0].name){
          console.log("iamhere")
          sh = this.all_shapes[j][1]
          console.log(sh);
          this.canvas.setActiveObject(sh)
          this.process("decrement")
          console.log("not here")
          break
        }
       }
     }
     else if(x[0].substring(0,1) == 'M'){
      let sh;
      for (let j=0; j<this.all_shapes.length; j++){
        if (x[0] == this.all_shapes[j].name){
          sh = this.all_shapes[j]
          this.canvas.setActiveObject(sh)
          this.canvas.getActiveObject().set("fill", "green");
          this.canvas.renderAll();
        }
      } 
    }
     if (x[1].substring(0,1) == 'Q'){
       let sh; 
       for (let j=0; j<this.all_shapes.length; j++){
         if (Array.isArray(this.all_shapes[j]) && x[1] == this.all_shapes[j][0].get("name")){
           sh = this.all_shapes[j][1]
           this.canvas.setActiveObject(sh)
           console.log("this is sh" + sh)
           this.process("increment")
           break;
         }
       }
     }else{
       console.log("kjdjkcn")
       let sh; 
       for (let j=0; j<this.all_shapes.length; j++){
         if (x[1] == this.all_shapes[j].name){
           sh = this.all_shapes[j]
           this.canvas.setActiveObject(sh)
           this.canvas.getActiveObject().set("fill", x[2]);
           this.canvas.renderAll();
         }
       } 
   }
}


  process(state: string){
    let tex_arr = this.canvas.getActiveObject().get("text").split(" ")
    let num = tex_arr[tex_arr.length-1]
    let new_num
    if (state == "decrement") {
      new_num = parseInt(num) - 1
    }else {
      new_num = parseInt(num) + 1
    }
    
    tex_arr[tex_arr.length-1] = new_num
    this.canvas.getActiveObject().set("text",tex_arr.join(" "))
    this.canvas.renderAll() 
  }

  join(){
    console.log("name is " + this.name_arr[0].substring(0,1))
    var angle = Math.atan((this.index_array[3]-this.index_array[1])/(this.index_array[2]-this.index_array[0]))
    console.log("angle is: " + angle*180/Math.PI)
    var length = Math.sqrt(Math.pow(this.index_array[3]-this.index_array[1],2)+Math.pow(this.index_array[2]-this.index_array[0],2))
    console.log(length)
    var line = this.line.generate(this.index_array, angle,this.gain)

    this.canvas.add(line);
    this.last = line;
    this.add_element();
    console.log(this.whole_map);

  }

  
  update_data(){
    try{
      this.index_array[0] = this.index_array[2]
      this.index_array[1] = this.index_array[3]
      this.name_arr[0] = this.name_arr[1]
      this.index_array[2] = this.canvas.getActiveObject().left
      this.index_array[3] = this.canvas.getActiveObject().top
      this.name_arr[1] = this.canvas.getActiveObject()._objects[0].name
    
      console.log(this.index_array)
      console.log(this.name_arr)
    }catch{} 
  }

  add_element(){
    console.log(this.name_arr[0]);
    if (this.whole_map.has(this.name_arr[0])) {
      var arr = this.whole_map.get(this.name_arr[0]);
      var arrg = this.whole_map.get(this.name_arr[0] + 'g')
      arr!.push(this.name_arr[1]);
      arrg!.push(this.gain)
      this.whole_map.set(this.name_arr[0], arr!)
      this.whole_map.set(this.name_arr[0]+'g', arrg!)
    }else {
      this.whole_map.set(this.name_arr[0], [this.name_arr[1]])
      this.whole_map.set(this.name_arr[0] + 'g', [this.gain])
    }
  }

  new(){
    this.http.post(this.new_request, "new").subscribe()
    location.reload()
  }

  process_reset(x : string){
    let tex_arr = this.canvas.getActiveObject().get("text").split(" ")
    let num = tex_arr[tex_arr.length-1]
    
    if(x=="first")
      tex_arr[tex_arr.length-1] = this.items
    else
      tex_arr[tex_arr.length-1] = 0
    this.canvas.getActiveObject().set("text",tex_arr.join(" "))
  }

  stop(){
    this.Stop = true;
    this.http.post(this.stop_request, "stop").subscribe()
  }

  m_reset(){
    let sh;
    for (let j=0; j<this.all_shapes.length; j++){
      if (this.all_shapes[j].name.substring(0,1) == 'M'){
        sh = this.all_shapes[j]
        this.canvas.setActiveObject(sh)
        this.canvas.getActiveObject().set("fill", "green");
        this.canvas.renderAll();
      }
    } 
  }

}
