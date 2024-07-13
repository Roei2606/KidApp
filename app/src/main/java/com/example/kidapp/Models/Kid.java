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
    //private String kId;
    //private String kidMail;
    private String phone;


    //public String getKidMail() {
    // return kidMail;
    // }

    public String getPhone() {
        return phone;
    }

    public Kid setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Kid setKidMail(String kidMail) {
        this.mail = kidMail;
        return this;
    }
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

//    public Kid(String kId) {
//        this.photosUri = new ArrayList<>();
//        this.ImmunizationRecords = new ArrayList<>();
//        this.events = new ArrayList<>();
//        this.kId = kId;
//    }

    public int getAge() {
        return age;
    }


    public String getfName() {
        return fName;
    }

    public Kid setfName(String fName) {
        this.fName = fName;
        return this;
    }


    @Override
    public Kid setUid(String kId) {
        super.uid = kId;
        return this;
    }



    public MyDate getBirthDate() {
        return birthDate;
    }

    public String getlName() {
        return lName;
    }


    public Kid setAge(int age) {
        this.age = age;
        return this;
    }

    public Kid setlName(String lName) {
        this.lName = lName;
        return this;
    }

    public Kid setBirthDate(int day, int month, int year) {
        this.birthDate = new MyDate(day, month, year);
        this.age = LocalDate.now().getYear() - this.birthDate.getYear();
        if (LocalDate.now().getMonthValue() < this.birthDate.getMonth()) {
            this.age--;
        } else if (LocalDate.now().getMonthValue() == this.birthDate.getMonth()) {
            if (LocalDate.now().getDayOfMonth() < this.birthDate.getDay()) {
                this.age--;
            }
        }

        return this;
    }

    public ArrayList<MyPhoto> getPhotosUri() {
        return photosUri;
    }


    public Kid setPhotos(ArrayList<MyPhoto> photosUri) {
        this.photosUri = photosUri;
        return this;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public Kid setProfilePhotoUri(String profilePhotoUri) {
        this.profilePhoto = profilePhotoUri;
        return this;
    }

    public ArrayList<ImmunizationRecord> getImmunizationRecords() {
        return ImmunizationRecords;
    }

    public Kid setImmunizationRecords(ArrayList<ImmunizationRecord> ImmunizationRecords) {
        this.ImmunizationRecords = ImmunizationRecords;
        return this;
    }

    public int getDoseNumber(ArrayList<ImmunizationRecord> records, String vaccineName) {
        int doseNumber = 1;
        for (ImmunizationRecord record : records) {
            if (record.getVaccineName().equalsIgnoreCase(vaccineName)) {
                doseNumber = record.getDoseNumber() + 1;
            }
        }
        return doseNumber;
    }

    public ArrayList<KidEvent> getEvents() {
        return events;
    }

    public Kid setEvents(ArrayList<KidEvent> events) {
        this.events = events;
        return this;
    }


    public Map<String, String> getPhotosUriMap() {
        Map<String, String> map = new HashMap<>();
        for (MyPhoto photo : this.getPhotosUri()) {
            map.put(photo.getpId(), photo.getPhotoUri().toString());
        }
        Log.d("050222", map.toString());
        return map;
    }

    public Map<String, ImmunizationRecord> getIRMap() {

        Map<String, ImmunizationRecord> map = new HashMap<>();
        for (ImmunizationRecord record : this.getImmunizationRecords()) {
            map.put(record.getIrID(), record);
        }
        return map;
    }

    public Map<String, KidEvent> getKEMap() {

        Map<String, KidEvent> map = new HashMap<>();
        for (KidEvent event : this.getEvents()) {
            map.put(event.geteId(), event);
        }
        return map;
    }


    public ArrayList<MyPhoto> convertMapToMyPhotoArrayList(Map<String, String> map) {
        ArrayList<MyPhoto> photos = new ArrayList<>();
        if (map == null)
            return photos;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            MyPhoto photo = new MyPhoto().setpId(entry.getKey()).setPhotoUri(Uri.parse(entry.getValue()));
            photos.add(photo);
        }
        return photos;
    }

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

    public ArrayList<ImmunizationRecord> convertIRtoArrayList(Map<String, ImmunizationRecord> map) {
        ArrayList<ImmunizationRecord> iR = new ArrayList<>();
        if (map == null)
            return iR;
        for (Map.Entry<String, ImmunizationRecord> entry : map.entrySet()) {
            Map<String, Object> immunizationData = (Map<String, Object>) entry.getValue();
            Map<String, Object> vdate = (Map<String, Object>) immunizationData.get("vdate");
            MyDate date = new MyDate(((Long) vdate.get("day")).intValue(), ((Long) vdate.get("month")).intValue(), ((Long) vdate.get("year")).intValue());
            ImmunizationRecord record = new ImmunizationRecord().setCreatorName((String) immunizationData.get("creatorName"))
                    .setDoseNumber(((Long) immunizationData.get("doseNumber")).intValue())
                    .setHMOName((String) immunizationData.get("hmoname"))
                    .setVaccinatorName((String) immunizationData.get("vaccinatorName"))
                    .setVaccineName((String) immunizationData.get("vaccineName"))
                    .setvdate(date) // Assuming vdate is stored as a Long
                    .setIrID((String) immunizationData.get("irID"));
            iR.add(record);
        }
        return iR;
    }

    public ArrayList<KidEvent> convertKEtoArrayList(Map<String, KidEvent> map) {
        ArrayList<KidEvent> kE = new ArrayList<>();
        if (map == null)
            return kE;
        for (Map.Entry<String, KidEvent> entry : map.entrySet()) {
            Map<String, Object> eventData = (Map<String, Object>) entry.getValue();
            Map<String, Object> edate = (Map<String, Object>) eventData.get("edate");
            MyDate date = new MyDate(((Long) edate.get("day")).intValue(), ((Long) edate.get("month")).intValue(), ((Long) edate.get("year")).intValue());
            Log.d("05233", eventData.get("eId") + "");
            KidEvent event = new KidEvent().seteId((String) eventData.get("eId"))
                    .setEDate(date)
                    .setEventTitle((String) eventData.get("eventTitle"));
            kE.add(event);
        }
        return kE;
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
        //this.getImmunizationRecords().add(new ImmunizationRecord("er", 1, new MyDate(2,2,2), "er", "er","fgh"));
        // this.getEvents().add(new KidEvent().setEDate(new MyDate(2,2,2)).setEventTitle("todo"));
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

    @Override
    public String getUid() {
        return super.uid;
    }

    @Override
    public String getMail() {
        return this.getPhone()+"@gmail.com";
    }

    @Override
    public String getPassword() {
        return super.uid;
    }

    public void setCurrnetEvent(KidEvent event, int position) {
        this.events.set(position, event);
    }
}
