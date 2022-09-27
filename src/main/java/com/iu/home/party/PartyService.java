package com.iu.home.party;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.io.ResolverUtil.IsA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.home.util.FileManger;
import com.iu.home.util.Pager;

@Service
public class PartyService {
	
	@Autowired
	private PartyDAO partyDAO;
	@Autowired
	private FileManger fileManger;
	
	public int setPartyFileAdd(PartyFileDTO partyFileDTO)throws Exception{
		return partyDAO.setPartyFileAdd(partyFileDTO);
	}
	
	public PartyListDTO getPartyDetail(PartyListDTO partyListDTO)throws Exception{
		return partyDAO.getPartyDetail(partyListDTO);
	}
	
	
	public List<PartyListDTO> getPartyList(Pager pager)throws Exception{
		Long totalCount = partyDAO.getPartyCount(pager);
		pager.getNum(totalCount);
		pager.getRowNum();
		return partyDAO.getPartyList(pager);
	}
	
	public int setPartyAdd(PartyListDTO partyListDTO, MultipartFile [] files, ServletContext servletContext)throws Exception{
		int result = partyDAO.setPartyAdd(partyListDTO);
		String path = "resources/upload/notice";
		
		for(MultipartFile multipartFile : files) {
			if(multipartFile.isEmpty()) {
				continue;
			}
			String fileName = fileManger.saveFile(servletContext, path, multipartFile);
			PartyFileDTO partyFileDTO = new PartyFileDTO();
			partyFileDTO.setFileName(fileName);
			partyFileDTO.setOriName(multipartFile.getOriginalFilename());
			partyFileDTO.setPartyNum(partyListDTO.getPartyNum());
			partyDAO.setPartyFileAdd(partyFileDTO);
		}
		
		return result;
	}
	
	//Party
	public int setPartyJoin(PartyDTO partyDTO)throws Exception{
		return partyDAO.setPartyJoin(partyDTO);	
	}
	
	public int setPartyAccept(PartyListDTO partyListDTO)throws Exception{
		return partyDAO.setPartyAccept(partyListDTO);
	}
	
	public int setPartyCancel(PartyDTO partyDTO)throws Exception{
		return partyDAO.setPartyCancel(partyDTO);
	}
	
	public List<PartyDTO> getPartyRequest(PartyDTO partyDTO)throws Exception{
		return partyDAO.getPartyRequest(partyDTO);
	}
	
}
