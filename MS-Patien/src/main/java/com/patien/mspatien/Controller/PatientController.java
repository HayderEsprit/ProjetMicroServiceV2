package com.patien.mspatien.Controller;

import com.patien.mspatien.DTO.*;

import com.patien.mspatien.Kafka.KafkaPatientProducer;
import com.patien.mspatien.Kafka.KafkaStringProducer;
import com.patien.mspatien.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    private final KafkaPatientProducer producer;


    private final KafkaStringProducer kafkaStringProducer;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDto createPatient(@RequestBody PatientRequestDto patientRequestDto) {
        return patientService.savePatient(patientRequestDto);
    }

    @GetMapping("/{id}")
    public PatientResponseDto getPatient(@PathVariable Long id) {
        return patientService.getPatient(id);
    }

    @GetMapping
    public List<PatientResponseDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PutMapping("/{id}")
    public PatientResponseDto updatePatient(
            @PathVariable Long id,
            @RequestBody PatientRequestDto patientRequestDto) {
        return patientService.updatePatient(id, patientRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }


    @GetMapping("/withRendezvousSansCircuitBreaker/{id}")
    public ResponseEntity<PatientWithRendezVousDto> getPatientWithRendezVous(
            @PathVariable Long id) {

        return ResponseEntity.ok(patientService.PatientWithRendezVousDtoSansCircuitBreaker(id));
    }

    @GetMapping("/withrendezvousAvecCircuitBreaker/{id}")
    public ResponseEntity<PatientWithRendezVousDto> getRendezVousByPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.PatientWithRendezVousDtoAvecCircuitBreaker(id));

    }

    @PostMapping("/send")
    public ResponseEntity<String> sendPatient(@RequestBody PatientDTO patientDTO) {
        // Afficher l'objet avant envoi
        System.out.println("=== OBJET À ENVOYER ===");
        System.out.println("PatientDTO reçu dans le contrôleur: " + patientDTO.toString());
        System.out.println("ID: " + patientDTO.getId());
        System.out.println("Nom: " + patientDTO.getNom());
        System.out.println("Prénom: " + patientDTO.getPrenom());
        System.out.println("Email: " + patientDTO.getEmail());
        System.out.println("========================");

        producer.sendPatientMessage(patientDTO);
        return ResponseEntity.ok("PatientDTO envoyé à Kafka: " + patientDTO.toString());
    }

    @PostMapping("/sendSstring")
    public ResponseEntity<String> sendSimpleMessage(@RequestBody String message) {
        kafkaStringProducer.sendSimpleMessage(message);
        return ResponseEntity.ok("Message simple envoyé à Kafka: " + message);
    }

}