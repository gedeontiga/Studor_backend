function displayText(job_intro, job_name, job_description, text_id_1, text_id_2, text_id_3){
    document.getElementById(text_id_1).innerHTML = ""

    if(text_id_2 != null)document.getElementById(text_id_2).innerHTML = ""
    if(text_id_3 != null)document.getElementById(text_id_3).innerHTML = ""
    
    // alert("hello")
    let count_1 = 0;
    let count_2 = 0;
    let count_3 = 0;
    let delay = 50;

    let interval = setInterval(function(){
        if(count_1 < job_intro.length){
            document.getElementById(text_id_1).innerHTML += job_intro.charAt(count_1);
            count_1++;
        }
        if(text_id_2 != null && count_1 >= job_intro.length && count_2 < job_name.length){
            document.getElementById(text_id_2).innerHTML += job_name.charAt(count_2);
            count_2++;
        }
        if(text_id_3 != null && count_2 >= job_name.length && count_3 < job_description.length){
            document.getElementById(text_id_3).innerHTML += job_description.charAt(count_3);
            count_3++;
        }
    }, delay)
}

displayText("Discovery the best job for you with the full details on how you can access to it and provide cost.", "Get all you have to know on establishment that provide training on your predicted job activity sector.", null, "text-details-1", "text-details-2", null)

