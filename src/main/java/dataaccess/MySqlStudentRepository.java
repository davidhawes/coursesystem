package dataaccess;

import domain.Course;
import domain.CourseType;
import domain.Student;
import exceptions.DatabaseException;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository{

    private Connection con;

    public MySqlStudentRepository() throws SQLException, ClassNotFoundException {
        this.con = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
    }


    @Override
    public Optional<Student> insert(Student entity) {
        Assert.notNull(entity);

        try {
            String sql = "INSERT INTO `students` (`vname`, `nname`, `birthdate`) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getVname());
            preparedStatement.setString(2, entity.getNname());
            preparedStatement.setDate(3, entity.getBirthDate());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                return Optional.empty();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if(generatedKeys.next()) {
                return this.getById(generatedKeys.getLong(1));
            } else {
                return Optional.empty();
            }

        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public Optional<Student> getById(Long id) {
        Assert.notNull(id);
        if(countStudentsInDbWithId(id)==0){
            return Optional.empty();
        } else {
            try {
                String sql = "SELECT * FROM `students` WHERE `id` = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();
                Student student = new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vname"),
                        resultSet.getString("nname"),
                        resultSet.getDate("birthdate")
                );
                return Optional.of(student);

            } catch (SQLException sqlException){
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM `students`";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentList = new ArrayList<>();
            while (resultSet.next()){
                studentList.add( new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vname"),
                        resultSet.getString("nname"),
                        resultSet.getDate("hours")

                ));
                return studentList;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error occured!");
        }
        return null;
    }

    @Override
    public Optional<Student> update(Student entity) {
        Assert.notNull(entity);

        String sql = "UPDATE `students` SET `vname`= ?, `nname` = ?, `birthdate`= ? WHERE `students`.`id`= ?";

        if (countStudentsInDbWithId(entity.getId())==0){
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, entity.getVname());
                preparedStatement.setString(2, entity.getNname());
                preparedStatement.setDate(3, entity.getBirthDate());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0){
                    return Optional.empty();
                } else {
                    return this.getById(entity.getId());
                }

            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql = "DELETE FROM `students` WHERE `id`= ?";

        try {
            if (countStudentsInDbWithId(id) == 1) {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException){
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    private int countStudentsInDbWithId(Long id){
        try {
            String countSql = "SELECT COUNT(*) FROM `students` WHERE `id`=?";
            PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
            preparedStatementCount.setLong(1,id);
            ResultSet resultSetCount = preparedStatementCount.executeQuery();
            resultSetCount.next();
            int studentCount = resultSetCount.getInt(1);
            return studentCount;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }

    }


    @Override
    public List<Student> findStudentByVname(String vname) {
        return null;
    }

    @Override
    public List<Student> findStudentByNname(String nname) {
        return null;
    }

    @Override
    public List<Student> findStudentByBirthdate(Date birthDate) {
        return null;
    }
}
