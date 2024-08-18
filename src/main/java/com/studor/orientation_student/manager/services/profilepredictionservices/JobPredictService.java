package com.studor.orientation_student.manager.services.profilepredictionservices;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.studor.orientation_student.entities.profilejobprediction.Job;
import com.studor.orientation_student.manager.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
@SessionScope
public class JobPredictService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> predictJob(){
        // TODO: more here
        return null;
    }

    public Map<String, Object> getPredictedJob(HttpSession session){
        String sessionEmail = session.getAttribute("email").toString();
        if (sessionEmail != null) {
            Job job = userRepository.findByEmail(sessionEmail).get().getProfil().getJob();
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("name", job.getNom());
            jobMap.put("description", job.getDescription());
            jobMap.put("dureeFormation", job.getTraining().getDuree());
            jobMap.put("coutFormation", job.getTraining().getCout());
            List<String> establishmentNameList = new ArrayList<>();
            job.getTraining().getEstablishments().forEach(establishment -> establishmentNameList.add(establishment.getNom()));
            jobMap.put("establishment", establishmentNameList);
            jobMap.put("salary", job.getSalaire());
            Blob jobImageBlob = job.getImage();
            byte[] jobImagebytes = null;
            try (InputStream inputStream = jobImageBlob.getBinaryStream()) {
                jobImagebytes = inputStream.readAllBytes();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            String jobImage = Base64.getEncoder().encodeToString(jobImagebytes);
            jobMap.put("jobImage", jobImage);
            jobMap.put("option", job.getOption().getNom());
            return jobMap;
        }
        else{
            return null;
        }
    }
}