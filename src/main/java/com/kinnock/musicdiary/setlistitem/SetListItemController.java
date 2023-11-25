package com.kinnock.musicdiary.setlistitem;

import com.kinnock.musicdiary.setlistitem.dto.SetListItemDTO;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemPostDTO;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemPutDTO;
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
@RequestMapping(path = "api/v1/set-list-items")
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

  @GetMapping(path = "{setListItemId}")
  public ResponseEntity<SetListItemDTO> getSetListItem(
      @PathVariable("setListItemId") Long setListItemId) {
    try {
      return new ResponseEntity<>(
          this.setListItemService.getSetListItemById(setListItemId),
          HttpStatus.OK
      );
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
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
  public ResponseEntity<Void> deleteSetListItem(
      @PathVariable("setListItemId") Long setListItemId) {
    this.setListItemService.deleteSetListItem(setListItemId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
