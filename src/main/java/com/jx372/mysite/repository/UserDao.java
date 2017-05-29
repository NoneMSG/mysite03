package com.jx372.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource datasource;
		
	public UserVo get( Long no ){
		
		//map을 resultType으로 사용하는 예제
		Map map = sqlSession.selectOne("user.getByNo2", no);
		System.out.println(map);
		
		UserVo userVo = sqlSession.selectOne("user.getByNo",no);
		return userVo;
	}
	
	public UserVo get( String email, String password ) {
		UserVo vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = 
				" select no, name" + 
				"   from user" + 
				"  where email=?" +
				"    and password = password(?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, email );
			pstmt.setString( 2, password );
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				
				vo = new UserVo();
				vo.setNo(no);
				vo.setName(name);
			}
			
 		}catch( SQLException e ) {
			e.printStackTrace();
		}finally{
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
	public boolean update( UserVo vo ) {
		int count = sqlSession.update("user.update",vo);
		return count==1;
	}
	
	public boolean insert( UserVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = 
				" insert" +
				"   into user" +
				" values (null, ?, ?, password(?), ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, vo.getName() );
			pstmt.setString( 2, vo.getEmail() );
			pstmt.setString( 3, vo.getPassword() );
			pstmt.setString( 4, vo.getGender() );
			
			int count = pstmt.executeUpdate();
			return count == 1;
			
		}catch( SQLException e ) {
			e.printStackTrace();
		}finally{
			try {
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
