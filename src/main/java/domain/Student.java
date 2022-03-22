package domain;

import exceptions.InvalidValueException;

import java.sql.Date;

public class Student extends BaseEntity{
    private String vname;
    private String nname;
    private Date birthDate;

    public Student(Long id, String vname, String nname, Date birthDate) {
        super(id);
        this.setVname(vname);
        this.setNname(nname);
        this.setBirthDate(birthDate);
    }

    public Student(String vname, String nname, Date birthDate) {
        super(null);
        this.setVname(vname);
        this.setNname(nname);
        this.setBirthDate(birthDate);
    }

    public void setVname(String vname) throws InvalidValueException {
        if(vname!=null && vname.length()>1){
            this.vname = vname;
        } else {
            throw new InvalidValueException("Vorname muss mindestens 2 Zeichen lang sein!");
        }
    }

    public void setNname(String nname) throws InvalidValueException {
        if(nname!=null && nname.length()>1){
            this.nname = nname;
        } else {
            throw new InvalidValueException("Nachname muss mindestens 2 Zeichen lang sein!");
        }
    }

        public void setBirthDate(Date birthDate) throws InvalidValueException {
        if (birthDate!=null){
            this.birthDate = birthDate;
        } else {
            throw new InvalidValueException("Startdatum darf nicht null / leer sein!");
        }
    }

    public String getVname() {
        return vname;
    }

    public String getNname() {
        return nname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "vname='" + vname + '\'' +
                ", nname='" + nname + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
