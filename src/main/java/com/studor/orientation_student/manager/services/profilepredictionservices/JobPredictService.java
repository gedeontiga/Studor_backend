package com.studor.orientation_student.manager.services.profilepredictionservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import com.studor.orientation_student.entities.Hobbies;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.profilejobprediction.Job;
import com.studor.orientation_student.manager.repositories.UserRepository;
import com.studor.orientation_student.manager.repositories.profilejobpredictrepository.JobRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@SessionScope
public class JobPredictService {

    private final String FLASK_API_URL = "http://localhost:5000/api/predict-job";
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public Map<String, Object> getJobFromDatabase(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Job job = user.getProfil().getJob();
        Map<String, Object> jobMap = jobMapRender(job);
        return jobMap;
    }

    public Map<String, Object> predictJob(Map<String, Object> data) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer index = 1;

        @SuppressWarnings("unchecked")
        Map<String, Float> notes = (Map<String, Float>) data.get("notes");

        for(String key : notes.keySet()){
            map.put("note_"+index, data.get(key));
            index++;
        };

        map.put("moyenne", data.get("average"));
        map.put("option", data.get("option"));
        
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer age = LocalDate.now().getYear() - user.getProfil().getDateDeNaissance().getYear();
        map.put("age", age);

        List<Hobbies> hobbies = user.getProfil().getHobbies();
        map.put("loisir_1", hobbies.get(0).getNom());
        map.put("loisir_2", hobbies.get(1).getNom());
        
        RestTemplate restTemplate = new RestTemplate();
        String jobName = restTemplate.postForObject(FLASK_API_URL, map, String.class);
        Job job = jobRepository.findByNomIgnoreCase(jobName);
        user.getProfil().setJob(job);
        userRepository.save(user);

        return jobMapRender(job);
    }

    private Map<String, Object> jobMapRender(Job job){
        Map<String, Object> jobMap = new HashMap<>();
        jobMap.put("name", job.getNom());
        jobMap.put("description", job.getDescription());
        jobMap.put("dureeFormation", job.getTraining().getDuree());
        jobMap.put("coutFormation", job.getTraining().getCout());
        List<String> establishmentNameList = new ArrayList<>();
        job.getTraining().getEstablishments().forEach(establishment -> establishmentNameList.add(establishment.getNom()));
        jobMap.put("establishment", establishmentNameList);
        jobMap.put("salary", job.getSalaire());
        String jobImagePath = job.getCheminImage();
        jobMap.put("jobImage", jobImagePath);
        jobMap.put("option", job.getSubdomain().getNom());
        return jobMap;
    }
}