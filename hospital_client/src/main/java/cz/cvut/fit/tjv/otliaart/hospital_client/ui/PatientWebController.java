package cz.cvut.fit.tjv.otliaart.hospital_client.ui;

import cz.cvut.fit.tjv.otliaart.hospital_client.data.PatientClient;
import cz.cvut.fit.tjv.otliaart.hospital_client.data.ProcedureClient;
import cz.cvut.fit.tjv.otliaart.hospital_client.model.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientWebController {
    private final PatientClient patientClient;
    private final ProcedureClient procedureClient;

    @Autowired
    public PatientWebController(PatientClient patientClient, ProcedureClient procedureClient) {
        this.patientClient = patientClient;
        this.procedureClient = procedureClient;
    }

    @GetMapping
    public String readAll(Model model) {
        model.addAttribute("patients", patientClient.readAll());
        return "patients";
    }

    @GetMapping("/add")
    public String create(Model model) {
        model.addAttribute("patient", new PatientDto());
        model.addAttribute("procedures", procedureClient.readAll()); // Add all procedures to the model
        return "patientAdd";
    }

    @PostMapping
    public String createSubmit(@ModelAttribute("patient") PatientDto patientDto, Model model) {
        model.addAttribute("patient", patientClient.create(patientDto));
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        model.addAttribute("patientDto", patientClient.readById(id));
        model.addAttribute("procedures", procedureClient.readAll());
        return "patientEdit";
    }

    @PatchMapping("/edit/{id}")
    public String editPatientSubmit(@ModelAttribute("patientDto") PatientDto patientDto, @PathVariable Long id) {
        patientClient.update(patientDto, id);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        patientClient.delete(id);
        return "redirect:/patients";
    }
}

