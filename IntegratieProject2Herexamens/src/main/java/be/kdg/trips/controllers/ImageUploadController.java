package be.kdg.trips.controllers;

import be.kdg.trips.model.TripImage;
import be.kdg.trips.service.TripImageService;
import be.kdg.trips.service.TripLocationService;
import be.kdg.trips.service.TripService;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
* Created by Matthias on 1/08/2015.
*/

@Controller
@RequestMapping("/")
public class ImageUploadController {
    @Autowired
    TripService tripService;
    @Autowired
    TripLocationService tripLocationService;
    @Autowired
    TripImageService imageService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/singleUpload/{tripId}/{tripLocationId}",method = RequestMethod.GET)
    public ModelAndView singleUpload(@PathVariable int tripId,@PathVariable int tripLocationId,ModelMap model){
        model.addAttribute("tripId",tripId);
        model.addAttribute("tripLocationId",tripLocationId);
        return new ModelAndView("singleUpload");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/singleUpload/{tripId}/{tripLocationId}", method= RequestMethod.POST )
    public @ResponseBody
    ModelAndView singleSave(@RequestParam("file") MultipartFile file,
                            @RequestParam(value = "desc",required = false) String desc ,
                            @PathVariable int tripLocationId,
                            @PathVariable int tripId,
                            HttpServletRequest request){
        String fileName = null;
        if (!file.isEmpty()) {
            try {
                TripImage tripImage = new TripImage();
                tripImage.setDescription(desc);
                tripImage.setTripLocation(tripLocationService.findTripLocationById(tripLocationId));
                imageService.saveTripImage(tripImage);
                int imgId = tripImage.getImageId();

                fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                String filePath = request.getSession().getServletContext().getRealPath("/")+"resources\\images\\"+imgId+"."+FilenameUtils.getExtension(fileName);;
                File newFile = new File(filePath);

                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(newFile));
                buffStream.write(bytes);
                BufferedImage img = ImageIO.read(newFile);
                BufferedImage thumbImg = Scalr.resize(img, Method.QUALITY, Scalr.Mode.AUTOMATIC,
                        100,
                        100, Scalr.OP_ANTIALIAS);
                //convert bufferedImage to outpurstream
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(thumbImg,FilenameUtils.getExtension(fileName),os);


                //or wrtite to a file
                String filePath2 = request.getSession().getServletContext().getRealPath("/")+"resources\\images\\thumb"+imgId+"."+FilenameUtils.getExtension(fileName);;

                File f2 = new File(filePath2);


                ImageIO.write(thumbImg, FilenameUtils.getExtension(fileName), f2);

                buffStream.close();

                tripImage.setImgUrl( "/Trips/resources/images/"+imgId+"."+FilenameUtils.getExtension(fileName));
                tripImage.setThumbUrl("/Trips/resources/images/thumb"+imgId+"."+FilenameUtils.getExtension(fileName));
                imageService.updateTripImage(tripImage);
                return new ModelAndView("redirect:/createLocation/"+tripId+"/"+tripLocationId);
            } catch (Exception e) {
                return new ModelAndView("singleUpload");
            }
        } else {
            return new ModelAndView("singleUpload");
        }
    }
}
