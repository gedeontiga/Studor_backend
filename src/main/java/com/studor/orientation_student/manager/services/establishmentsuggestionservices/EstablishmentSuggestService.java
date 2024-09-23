package com.studor.orientation_student.manager.services.establishmentsuggestionservices;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.establishmentsuggestion.Establishment;
import com.studor.orientation_student.entities.establishmentsuggestion.Training;
import com.studor.orientation_student.entities.profilejobprediction.Job;
import com.studor.orientation_student.manager.repositories.UserRepository;
import com.studor.orientation_student.manager.repositories.establishmentsuggestcontroller.TrainingRepository;

@Service
@SessionScope
public class EstablishmentSuggestService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> suggestEstablishmentOnJob() {

            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Job job = user.getProfil().getJob();
            List<Establishment> establishments = job.getTraining().getEstablishments();
            
            if (user.getProfil().getEstablishments().isEmpty()) {
                List<Establishment> currentEstablishments = new ArrayList<>();
                currentEstablishments.add(establishments.get(0));
                System.out.println(establishments);
                user.getProfil().setEstablishments(currentEstablishments);
                userRepository.save(user);
            }

            return establishmentInfoMapRender(establishments, job);
    }

    public Map<String, Object> getEstablishmentOnJob() {

            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Establishment> establishments = user.getProfil().getJob().getTraining().getEstablishments();

            return establishmentMapRender(establishments);
    }

    private Map<String, Object> establishmentInfoMapRender(List<Establishment> establishments, Job job){
        // Hash which i send to JS using ajax
        Map<String, Object> establishmentMap = new HashMap<>();
        establishmentMap.put("name", establishments.get(0).getNom());
        establishmentMap.put("establishmentLocation", establishments.get(0).getLocalisation());
        establishmentMap.put("directorName", establishments.get(0).getNomDirecteur());

        Blob establishmentImageBlob = establishments.get(0).getImage();
        byte[] establishmentImagebytes = null;
        try (InputStream inputStream = establishmentImageBlob.getBinaryStream()) {
            establishmentImagebytes = inputStream.readAllBytes();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        String establishmentImage = Base64.getEncoder().encodeToString(establishmentImagebytes);
        establishmentMap.put("establishmentImage", establishmentImage);

        Training training = trainingRepository.findByJob(job);

        establishmentMap.put("trainingName", training.getNom());
        establishmentMap.put("trainingCost", training.getCout());
        establishmentMap.put("option", training.getJob().getSubdomain().getNom());
        establishmentMap.put("domain", training.getJob().getSubdomain().getDomain().getNom());

        List<String> trainingMatterName = new ArrayList<>();
        training.getMatters().forEach(matter -> trainingMatterName.add(matter.getNom()));
        establishmentMap.put("trainingMatterName", trainingMatterName);

        List<Integer> trainingMatterCredit = new ArrayList<>();
        training.getMatters().forEach(matter -> trainingMatterCredit.add(matter.getCoef()));
        establishmentMap.put("trainingMatterCredit", trainingMatterCredit);

        return establishmentMap;
    }

    private Map<String, Object> establishmentMapRender(List<Establishment> establishments){
        Map<String, Object> establishmentMap = new HashMap<>();
        List<String> establishmensNameList = new ArrayList<>();
        establishments.forEach(establishment -> establishmensNameList.add(establishment.getNom()));
        establishmentMap.put("establishmentName", establishmensNameList);

        List<String> establishmentImageList = new ArrayList<>();
        establishments.forEach(establishment -> {
            Blob establishmentImageBlob = establishment.getImage();
            byte[] establishmentImagebytes = null;

            try (InputStream inputStream = establishmentImageBlob.getBinaryStream()) {
                establishmentImagebytes = inputStream.readAllBytes();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            String establishmentImage = Base64.getEncoder().encodeToString(establishmentImagebytes);
            establishmentImageList.add(establishmentImage);
        });
        establishmentMap.put("establishmentImageList", establishmentImageList);

        List<String> establishmentLocation = new ArrayList<>();
        establishments.forEach(establishment -> establishmentLocation.add(establishment.getLocalisation()));
        establishmentMap.put("establishmentLocationList", establishmentLocation);

        List<String> establishmentDirectorNameList = new ArrayList<>();
        establishments.forEach(establishment -> establishmentDirectorNameList.add(establishment.getNomDirecteur()));
        establishmentMap.put("directorNameList", establishmentDirectorNameList);

        List<String> establishmentDomainNameList = new ArrayList<>();
        establishments.forEach(establishment -> establishment.getDomains().forEach(domain -> {
            if (!establishmentDomainNameList.contains(domain.getNom())) {
                establishmentDomainNameList.add(domain.getNom());
            }
        }));
        System.out.println(establishmensNameList);
        establishmentMap.put("estabishmentDomainNameList", establishmentDomainNameList);

        return establishmentMap;
    }
}
