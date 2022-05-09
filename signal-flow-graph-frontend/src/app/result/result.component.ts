import { Component, Injectable, OnInit } from '@angular/core';
import { fabric } from 'fabric';

@Injectable({
  providedIn : 'root'
})
export class ResultComponent {

  
i = 1;
top = 500;
left = 500;
res_to_show = "";
n_loops !: any[];
delta !: any[];
x = 0
y = 5
//transfer_function : string, forward_paths:string, individual_loops:string, non_touching_loops:string
  generate(res : string[]){
    console.log("iam hereeeee " + res.length);
    this.res_to_show += " Result : " + res[(res.length)-1];
    this.res_to_show += '\n Forward paths are : ' + res[0];
    this.res_to_show += '\n Individual Loops paths are : ' + res[1];

    if (res[2].length != 0) {
      this.n_loops = res[2].split("$");

      for (let j=0; j <this.n_loops.length; j++){
        this.res_to_show += '\n ' + j+2 +' Non-touching loops are : ' + this.n_loops[j];
      }
  }


  if (res[3].length != 0){
      this.delta = res[3].split("$");

      for (let k=0; k <this.delta.length; k++){
        this.res_to_show += '\n Delata '+ (k+1) + ' is : ' + this.delta[k];
      }
  }





    var result = new fabric.Text((
      this.res_to_show
      ),{
      fill : 'black',
      top : this.top+this.y,
      left : this.left+5,
      fontSize : 30,
      fontStyle : 'oblique'
  })

  //var ret_arr = [text]

  this.i += 1;
  if(this.i>9){
    this.x=12
    this.y = 15
  }
    return result;
  }

}


/*
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}*/


/*
' Result  : '+ transfer_function +
      '\n Forward paths are : ' + forward_paths +
      '\n Individual loops are : ' + individual_loops +
      '\n Non touching loops are : ' + non_touching_loops
      */