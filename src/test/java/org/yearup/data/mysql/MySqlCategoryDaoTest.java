package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCategoryDaoTest extends BaseDaoTestClass {
    private MySqlCategoryDao dao;

    @BeforeEach
    public void setup() {
        dao = new MySqlCategoryDao(dataSource);
    }


    @Test
    public void update_shouldUpdate_productInformation() {
        //arrange

        String sql = "UPDATE categories SET name = ? WHERE Category_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getName());
            statement.setInt(2, categoryId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //act

        //assert

    }

    @Test
    public void getById_shouldGet_categoryInformation() {
        //arrange
        int categoryId = 1;
        var expected = new Category() {
            {
                setCategoryId(1);
                setName("Kitchen");
                setDescription("supplies");
            }};
        //act
        var actual = dao.getById(categoryId);
        //assert
        assertEquals(expected.getCategoryId(), actual.getCategoryId(), "Because I tried to get product 1 from the database.");
    }

    @Test
    public void create_shouldCreate_newInformation() {
        //arrange
        //act
        //assert
    }
}
