package com.kinnock.musicdiary.setListItem;

import com.kinnock.musicdiary.setListItem.dto.SetListItemDTO;
import com.kinnock.musicdiary.setListItem.dto.SetListItemPostDTO;
import com.kinnock.musicdiary.setListItem.dto.SetListItemPutDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/setListItem")
public class SetListItemController {
  private final SetListItemService setListItemService;

  @Autowired
  public SetListItemController(SetListItemService setListItemService) {
    this.setListItemService = setListItemService;
  }

  @PostMapping
  public ResponseEntity<SetListItemDTO> createSetListItem(
      @RequestBody SetListItemPostDTO setListItemPostDTO
  ) {
    return new ResponseEntity<>(
        this.setListItemService.createSetListItem(setListItemPostDTO),
        HttpStatus.OK
    );
  }

  @GetMapping(path = "{SetListItemDTO}")
  public ResponseEntity<SetListItemDTO> getSetListItem(
      @PathVariable("setListItemId") Long setListItemId) {
    return new ResponseEntity<>(
        this.setListItemService.getSetListItemById(setListItemId),
        HttpStatus.OK
    );
  }

  @GetMapping
  public ResponseEntity<List<SetListItemDTO>> getSetListItems() {
    return new ResponseEntity<>(this.setListItemService.getAllSetListItems(), HttpStatus.OK);
  }

  @PutMapping(path = "{setListItemId}")
  public ResponseEntity<SetListItemDTO> updateSetListItem(
      @PathVariable("setListItemId") Long setListItemId,
      @RequestBody SetListItemPutDTO setListItemPutDTO
  ) {
    return new ResponseEntity<>(
        this.setListItemService.updateSetListItem(setListItemId, setListItemPutDTO),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = "{setListItemId}")
  public ResponseEntity<SetListItemDTO> deleteSetListItem(
      @PathVariable("setListItemId") Long setListItemId) {
    return new ResponseEntity<>(
        this.setListItemService.deleteSetListItem(setListItemId),
        HttpStatus.OK
    );
  }

}
