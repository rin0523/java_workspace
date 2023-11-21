package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements DAO {

	// DB와 연결
	// DB와 실제 연결하는 구문

	// DB와 연결하는 역할
	private Connection conn;
	// sql구문을 실행시키는 기능을 갖는 객체
	private PreparedStatement pst;
	// 쿼리문 저장
	private String query = "";

	public ProductDAOImpl() {
		// DB 연결 정보를 설정하는 class 싱글톤으로 생성
		DatabaseConnection dbc = DatabaseConnection.getInstance();
		conn = dbc.getConnection();
	}

	// sql 구문 처리
	// executeWuery() /select 구문에서 사용 ResultSet이 리턴
	// evecuteUpdate() /insert, update,delete 별도의 리턴이 없고, 0/1로만 리턴

	@Override
	public int insert(Product p) {
		// db와 직접 연결
		System.out.println("insert DAO success!! ");
		query = "insert into product(pname,price,madeby)values(?,?,?)";
		try {
			pst = conn.prepareStatement(query);
			// ? 값을 setting
			pst.setString(1, p.getPname());
			pst.setInt(2, p.getPrice());
			pst.setString(3, p.getMadeby());

			return pst.executeUpdate();

		} catch (SQLException e) {
			System.out.println("insert error");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Product> selectList() {
		// TODO Auto-generated method stub
		System.out.println("list DAO success!!");
		query = "select*from product order by pno desc";
		List<Product> list = new ArrayList<Product>();

		try {
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int pno = rs.getInt("pno");
				list.add(new Product(pno, rs.getString("pname"), rs.getInt("price")));
			}
			return list;

		} catch (SQLException e) {
			System.out.println("list error");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Product selectOne(int pno) {
		// TODO Auto-generated method stub
		System.out.println("detail DAO success!!");
		query = "select*from product where pno=?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, pno);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return new Product(rs.getInt("pno"), rs.getString("pname"), rs.getInt("price"), rs.getString("regdate"),
						rs.getString("madeby"));
			}
		} catch (SQLException e) {
			System.out.println("detail error");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(Product p) {
		// TODO Auto-generated method stub
		System.out.println("update DAO success!! ");
		query = "update product set pname = ? , price=?, regdate=now(), madeby=? where pno=? ";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, p.getPname());
			pst.setInt(2, p.getPrice());
			pst.setString(3, p.getMadeby());
			pst.setInt(4, p.getPno());
			// insert,update,delete : executeUpdate();
			return pst.executeUpdate();

		} catch (SQLException e) {
			System.out.println("update error");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(int pno) {
		// TODO Auto-generated method stub
		System.out.println("delete dao success!! ");
		query="delete from product where pno=?";
		try {
			pst=conn.prepareStatement(query);
			pst.setInt(1, pno);
			return pst.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("delete error");
			e.printStackTrace();
		}
		return 0;
	}

}
