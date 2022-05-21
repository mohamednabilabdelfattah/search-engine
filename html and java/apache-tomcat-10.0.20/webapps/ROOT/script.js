const paginationResults = document.querySelector(".Pagination-Results").children;
const prev=document.querySelector(".prev");
const next=document.querySelector(".next");
const page=document.querySelector(".page-num");
const maxResults=10;
let index=1;
 
 const pagination=Math.ceil(paginationResults.length/maxResults);
 console.log(pagination);

 prev.addEventListener("click",function(){
   index--;
   check();
   showResults();
 });
 next.addEventListener("click",function(){
     index++;
     check();
     showResults();  
 })

 function check(){
      if(index==pagination){
          next.classList.add("disabled");
      }
      else{
        next.classList.remove("disabled");  
      }

      if(index==1){
          prev.classList.add("disabled");
      }
      else{
        prev.classList.remove("disabled");  
      }
 }

function showResults() {
    for(let i=0;i<paginationResults.length; i++){
        paginationResults[i].classList.remove("show");
        paginationResults[i].classList.add("hide");
        if(i>=(index*maxResults)-maxResults && i<index*maxResults){
            paginationResults[i].classList.remove("hide");
            paginationResults[i].classList.add("show");
        }
        page.innerHTML=index;
    }         
}

window.onload=function(){
    showResults();
    check();
}