package cz.cvut.fit.tjv.otliaart.hospital_client.ui;

import cz.cvut.fit.tjv.otliaart.hospital_client.data.ProcedureClient;
import cz.cvut.fit.tjv.otliaart.hospital_client.model.PatientDto;
import cz.cvut.fit.tjv.otliaart.hospital_client.model.ProcedureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/procedures")
public class ProcedureWebController {
    private final ProcedureClient procedureClient;

    @Autowired
    public ProcedureWebController(ProcedureClient procedureClient) {
        this.procedureClient = procedureClient;
    }

    @GetMapping
    public String readAll(Model model) {
        model.addAttribute("procedures", procedureClient.readAll());
        return "procedures";
    }

    @GetMapping("/add")
    public String create(Model model) {
        model.addAttribute("procedure", new ProcedureDto());
        return "procedureAdd";
    }

    @PostMapping
    public String createSubmit(@ModelAttribute("procedure") ProcedureDto procedureDto, Model model) {
        model.addAttribute("procedure", procedureClient.create(procedureDto));
        return "redirect:/procedures";
    }

    @GetMapping("/edit/{id}")
    public String editProcedure(@PathVariable Long id, Model model) {
        model.addAttribute("procedureDto", procedureClient.readById(id));
        return "procedureEdit";
    }

    @PatchMapping("/edit/{id}")
    public String editProcedureSubmit(@ModelAttribute("procedureDto") ProcedureDto procedureDto, @PathVariable Long id) {
        procedureClient.update(procedureDto, id);
        return "redirect:/procedures";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        procedureClient.delete(id);
        return "redirect:/procedures";
    }

    @GetMapping("/{id}/patients")
    public String getPatientsByProcedureId(@PathVariable Long id, Model model) {
        List<PatientDto> patients = procedureClient.getPatientsByProcedureId(id);
        model.addAttribute("patients", patients);
        model.addAttribute("procedureId", id);
        return "procedurePatients";
    }


}
