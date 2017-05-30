package com.jx372.mysite.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public void increaseGroupOrder(Integer gNo, Integer oNo){
		Map<String, Integer> map = new  HashMap<String, Integer>();
		map.put("groupNo", gNo);
		map.put("orderNo", oNo);
		
	}
	
	public int getTotalCount() {
		return sqlSession.selectOne("board.totalCount");
	}
	
	public List<BoardVo> getList( Integer page, Integer size ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "page", page-1 );
		map.put( "size", size );
		
		return sqlSession.selectList( "board.getList2", map );
	}
		
	public BoardVo get(Long no){
		BoardVo boardvo = sqlSession.selectOne("board.getbyNo",no);
		return boardvo;
	}
	
	public List<BoardVo> getList() {
		List<BoardVo> list = sqlSession.selectList("board.getList");
		return list;
	}
	
	public boolean delete( BoardVo vo ) {
		int count = sqlSession.delete("board.delete",vo);
		return count==1;
	}
	
	public boolean modify(BoardVo vo){
		int count = sqlSession.update("board.modify", vo);
		return count==1;
	}
	
	public boolean update(Long no){
		int count = sqlSession.update("board.update", no);
		return count==1;
	}
	
	public boolean insert( BoardVo vo ) {
		int count = sqlSession.insert("board.insert",vo);
		return count==1;
	}
	
//	public int getTotalCount( String keyword ) {
//		int totalCount = 0;
//
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = getConnection();
//			if( "".equals( keyword ) ) {
//				String sql = "select count(*) from board";
//				pstmt = conn.prepareStatement(sql);
//			} else { 
//				String sql =
//					"select count(*)" +
//					"  from board" +
//					" where title like ? or content like ?";
//				pstmt = conn.prepareStatement(sql);
//				
//				pstmt.setString(1, "%" + keyword + "%");
//				pstmt.setString(2, "%" + keyword + "%");
//			}
//			rs = pstmt.executeQuery();
//			if( rs.next() ) {
//				totalCount = rs.getInt( 1 );
//			}
//		} catch (SQLException e) {
//			System.out.println( "error:" + e );
//		} finally {
//			try {
//				if( rs != null ) {
//					rs.close();
//				}
//				if( pstmt != null ) {
//					pstmt.close();
//				}
//				if( conn != null ) {
//					conn.close();
//				}
//			} catch ( SQLException e ) {
//				System.out.println( "error:" + e );
//			}  
//		}
//		
//		return totalCount;
//	}
	
	
}
