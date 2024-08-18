package com.studor.orientation_student.manager.services.suggestionCour;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.studor.orientation_student.entities.suggestionCourEntities.DataModel;

public class DataGeneration {
    public List<DataModel> generateRandomData(int count) {
        // Random random = new Random();
        List<DataModel> dataList = new ArrayList<>();
        List<String> statutProfessionnel = new ArrayList<>();
        List<String> moyenneScolaire = new ArrayList<>();
        List<String> nomEmpoitemps = new ArrayList<>();
        List<String> niveauAcademiques = new ArrayList<>();

        statutProfessionnel.add("junior");
        statutProfessionnel.add("cenior");
        statutProfessionnel.add("junior_intermédiaire");
        statutProfessionnel.add("cenior_intermédiaire");
        statutProfessionnel.add("professionnel");
        statutProfessionnel.add("amateur");

        moyenneScolaire.add("passable");
        moyenneScolaire.add("assez_bien");
        moyenneScolaire.add("bien");
        moyenneScolaire.add("tres_bien");
        moyenneScolaire.add("excellent");

        nomEmpoitemps.add("e1");
        nomEmpoitemps.add("e2");
        nomEmpoitemps.add("e3");
        nomEmpoitemps.add("e4");
        nomEmpoitemps.add("e5");
        nomEmpoitemps.add("e6");
        nomEmpoitemps.add("e7");
        nomEmpoitemps.add("e8");
        nomEmpoitemps.add("e9");
        nomEmpoitemps.add("e10");

        niveauAcademiques.add("l1");
        niveauAcademiques.add("l2");
        niveauAcademiques.add("l3");
        niveauAcademiques.add("m1");
        niveauAcademiques.add("m2");
        niveauAcademiques.add("d1");
        niveauAcademiques.add("d2");
        niveauAcademiques.add("d3");

        for (int i = 0; i < count; i++) {
            DataModel data = new DataModel();
            data.setStatutProfessionnel(getRandomValue(statutProfessionnel));
            data.setMoyenneScolaire(getRandomValue(moyenneScolaire));
            data.setNomEmpoitemps(getRandomValue(nomEmpoitemps));
            data.setNiveauAcademique(getRandomValue(niveauAcademiques));
            dataList.add(data);
        }

        return dataList;
    }

    private String getRandomValue(List<String> values) {
        Random random = new Random();
        int index = random.nextInt(values.size());
        return values.get(index);
    }

    public void writeARFFFile(List<DataModel> dataList, int numFile) throws IOException {
        GetAbsoluePath getAbsoluePath = new GetAbsoluePath();
        String path = getAbsoluePath.getAbsolutePathOfFile();
        if (numFile == 1)
            path = path + "/data/suggestionCourData/dataForTrainning.arff";
        if (numFile == 0)
            path = path + "/data/suggestionCourData/dataForPrediction.arff";

        FileWriter writer = new FileWriter(path);

        writer.write("@relation SugestionCourEmploiTemps\n\n");
        writer.write(
                "@attribute statutProfessionnel {junior, cenior, amateur, junior_intermédiaire, cenior_intermédiaire, professionnel}\n");
        writer.write("@attribute moyenneScolaire {passable, assez_bien, bien, tres_bien, excellent}\n");
        writer.write("@attribute niveauAcademique {l1,l2,l3,m1,m2,d1,d2,d3}\n");
        writer.write("@attribute nomEmpoitemps {e1,e2,e3,e4,e5,e6,e7,e8,e9,e10}\n\n");
        writer.write("@data\n");

        for (DataModel data : dataList) {
            writer.write(data.getStatutProfessionnel() + ",");
            writer.write(data.getMoyenneScolaire() + ",");
            writer.write(data.getNiveauAcademique() + ",");
            writer.write(data.getNomEmpoitemps() + "\n");
        }

        writer.close();
    }
}
