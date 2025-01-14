package com.getyourguide.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActivityController {
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/debug")
    public void debug(@RequestParam(name = "title", required = false, defaultValue = "NONE") String title, Model model) {
        try {
            model.addAttribute("title", title);
            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            //read JSON file and convert to a list of activities
            var fileInputStream  = new ClassPathResource("static/activities.json").getInputStream();
            var activities = objectMapper.readValue(fileInputStream, new TypeReference<List<Activity>>() {
            });
            model.addAttribute("activities", activities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> activities() {
        List<Activity> activities = null;
        Map<Long, Supplier> suppliers = null;
        try {
            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            //read JSON file and convert to a list of activities
            var fileInputStream = new ClassPathResource("static/activities.json").getInputStream();
            activities = objectMapper.readValue(fileInputStream, new TypeReference<List<Activity>>() {
            });

            var suppliersInputStream = new ClassPathResource("static/suppliers.json").getInputStream();
            List<Supplier> supplierList = objectMapper.readValue(suppliersInputStream, new TypeReference<List<Supplier>>() {});
            suppliers = supplierList.stream().collect(Collectors.toMap(Supplier::getId, Function.identity()));

            final Map<Long, Supplier> finalSuppliers = suppliers;
            activities.forEach(activity -> {
                Supplier supplier = finalSuppliers.get(activity.getSupplierId());
                if (supplier != null) {
                    activity.setSupplierName(supplier.getName());
                    String supplierLocation = String.format("%s, %s, %s, %s",
                            supplier.getAddress(), supplier.getZip(), supplier.getCity(), supplier.getCountry());
                    activity.setSupplierLocation(supplierLocation);
                }
            });

            return ResponseEntity.ok(activities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/activities/search")
    public ResponseEntity<List<Activity>> searchActivities(String title) {
        List<Activity> activities = null;
        Map<Long, Supplier> suppliers = null;
        try {
            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            //read JSON file and convert to a list of activities
            var fileInputStream = new ClassPathResource("static/activities.json").getInputStream();
            activities = objectMapper.readValue(fileInputStream, new TypeReference<List<Activity>>() {
            });

            activities = activities.stream()
                    .filter(activity -> activity.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());

            var suppliersInputStream = new ClassPathResource("static/suppliers.json").getInputStream();
            List<Supplier> supplierList = objectMapper.readValue(suppliersInputStream, new TypeReference<List<Supplier>>() {});
            suppliers = supplierList.stream().collect(Collectors.toMap(Supplier::getId, Function.identity()));

            final Map<Long, Supplier> finalSuppliers = suppliers;
            activities.forEach(activity -> {
                Supplier supplier = finalSuppliers.get(activity.getSupplierId());
                if (supplier != null) {
                    activity.setSupplierName(supplier.getName());
                    String supplierLocation = String.format("%s, %s, %s, %s",
                            supplier.getAddress(), supplier.getZip(), supplier.getCity(), supplier.getCountry());
                    activity.setSupplierLocation(supplierLocation);
                }
            });

            return ResponseEntity.ok(activities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
