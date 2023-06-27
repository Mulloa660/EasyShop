package org.yearup.data.mysql;


import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.data.mysql.MySqlDaoBase;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {

    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection connection = getConnection();

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Category category = new Category(categoryId, name, description);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // get all categories
        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        String sql = "SELECT * FROM categories WHERE Category_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    categoryId = resultSet.getInt("Category_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Category category = new Category(categoryId, name, description);
                    return category;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // get category by id
        return null;
    }

    @Override
    public Category create(Category category) {
        // create a new category
        String sql = "INSERT INTO categories (name) VALUES (?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, category.getName());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    category.setCategoryId(generatedId);
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public void update(int categoryId, Category category) {
            String sql = "UPDATE categories SET name = ? WHERE Category_id = ?";

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, category.getName());
                statement.setInt(2, categoryId);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        // update category
    }

    @Override
    public void delete(int categoryId) {

        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category() {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
