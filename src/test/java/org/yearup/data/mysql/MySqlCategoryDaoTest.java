package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MySqlCategoryDaoTest extends BaseDaoTestClass {
    private MySqlCategoryDao dao;

    @BeforeEach
    public void setup() {
        dao = new MySqlCategoryDao(dataSource);
    }


    @Test
    public void update_shouldUpdate_categoryInformation() {
        //arrange
        var updatedCategory = new Category() {{
            setCategoryId(2);
            setName("Fashion");
            setDescription("Testing");
        }};
        //act
        dao.update(updatedCategory.getCategoryId(), updatedCategory);

        //assert

        Category actual = dao.getById(updatedCategory.getCategoryId());
        assertEquals(updatedCategory.getDescription(), actual.getDescription());

    }

    @Test
    public void getById_shouldGet_categoryInformation() {
        //arrange
        int categoryId = 1;
        var expected = new Category() {
            {
                setCategoryId(1);
                setName("Electronics");
                setDescription("Explore the latest gadgets and electronic devices.");
            }
        };
        //act
        var actual = dao.getById(categoryId);
        //assert
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void create_shouldCreate_newInformation() {
        //arrange


    }
}
