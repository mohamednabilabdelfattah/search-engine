const searchForm = document.getElementById("AKMLRequest");
const searchInput = document.getElementById("textQuery");
const micBtn = document.getElementById("mic");

const speechRecognition = window.speechRecognition || window.webkitSpeechRecognition;

if (speechRecognition){

    console.log("speech recognition is supported");
    const recognition = new speechRecognition();
    console.log("here");

    micBtn.addEventListener("click", function(){
        recognition.start();
        console.log("here");
    })

    recognition.addEventListener("start",function(){
        searchInput.focus();
        console.log("here");
    })

    recognition.addEventListener("end", function(){
        searchInput.focus();
        console.log("here");
    })

    recognition.addEventListener("result", function(event){
        console.log("here");
        //console.log(event);
        const transcript = event.results[0][0].transcript;
        searchInput.value = transcript;
        console.log(transcript);
        setTimeout(()=>{
            searchForm.submit();
        },500)
    })
    
}else{
    console.log("speech recognition is not supported");
}