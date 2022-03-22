package dataaccess;

import domain.Student;

import java.sql.Date;
import java.util.List;

public interface MyStudentRepository extends  BaseRepository<Student, Long>{

    List<Student> findStudentByVname(String vname);

    List<Student> findStudentByNname(String nname);

    List<Student> findStudentByBirthdate(Date birthDate);



}
