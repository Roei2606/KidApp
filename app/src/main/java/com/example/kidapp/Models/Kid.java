package com.example.kidapp.Models;

import android.net.Uri;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.kidapp.ExternalModels.boundaries.ObjectBoundary;
import com.example.kidapp.ExternalModels.utils.CreatedBy;
import com.example.kidapp.ExternalModels.utils.ObjectId;
import com.example.kidapp.ExternalModels.utils.UserId;


public class Kid  extends BasicUser {
    private String fName;
    private String lName;
    private MyDate birthDate;
    private int age;
    private ArrayList<MyPhoto> photosUri;
    private String profilePhoto;
    private ArrayList<ImmunizationRecord> ImmunizationRecords;
    private ArrayList<KidEvent> events;
    private String phone;



    public Kid (String phone) {
        this.photosUri = new ArrayList<>();
        this.ImmunizationRecords = new ArrayList<>();
        this.events = new ArrayList<>();
        super.uid = UUID.randomUUID().toString().toUpperCase();
        this.phone = phone;
        super.mail = phone+"@gmail.com";
    }

    public Kid() {
        this.photosUri = new ArrayList<>();
        this.ImmunizationRecords = new ArrayList<>();
        this.events = new ArrayList<>();
        super.uid = UUID.randomUUID().toString().toUpperCase();
        this.phone = "-1";
        String temp =super.uid.replace("-", "");
        temp=temp.toLowerCase();
        super.mail = temp+"@gmail.com";

    }

    @Override
    public Kid setUid(String kId) {
        super.uid = kId;
        return this;
    }

    public MyDate getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public ArrayList<MyPhoto> getPhotosUri() {
        return photosUri;
    }

    public String getProfilePhoto() {return profilePhoto;}

    public ArrayList<ImmunizationRecord> getImmunizationRecords() {
        return ImmunizationRecords;
    }

    public ArrayList<KidEvent> getEvents() {
        return events;
    }

    @Override
    public String getUid() {return super.uid;}

    @Override
    public String getMail() {return this.getPhone()+"@gmail.com";}

    @Override
    public String getPassword() {return super.uid;}

    public void setCurrnetEvent(KidEvent event, int position) {this.events.set(position, event);}

    @Override
    public String toString() {
        return "Kid{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", photosUri=" + photosUri +
                ", profilePhotoUri=" + profilePhoto +
                ", ImmunizationRecords=" + ImmunizationRecords +
                ", events=" + events +
                ", uid='" + super.uid + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public ObjectBoundary toBoundary() {
        ObjectBoundary objectBoundary = new ObjectBoundary();
        objectBoundary.setType(this.getClass().getSimpleName());
        objectBoundary.setAlias(this.getPhone());
        objectBoundary.setObjectId(new ObjectId());
        objectBoundary.setActive(true);
        CreatedBy user = new CreatedBy();
        user.setUserId((new UserId()).setEmail(this.getMail()));
        objectBoundary.setCreatedBy(user);
        Map<String, Object> details = new HashMap<>();
        details.put("immunization_records", this.getImmunizationRecords());
        details.put("events", this.getEvents());
        details.put("photos", this.getPhotosUri());
        details.put("profilePhoto", this.getProfilePhoto());
        details.put("fName", this.getfName());
        details.put("lName", this.getlName());
        details.put("uid", this.getUid());
        details.put("mail", this.getMail());
        details.put("birthDate", this.getBirthDate());
        details.put("phone", this.getPhone());
        objectBoundary.setObjectDetails(details);
        return objectBoundary;
    }
}
