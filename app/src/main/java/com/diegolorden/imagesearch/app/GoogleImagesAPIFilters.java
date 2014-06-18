package com.diegolorden.imagesearch.app;

public class GoogleImagesAPIFilters {
    public String imageType;
    public String imageSize;
    public String imageColor;
    public String imageSite;

    public String toString() {
        String params = "";
        if (imageType != null && !imageType.equals("Any")) {
            params += "imgtype=" + imageType.toLowerCase().replace(" ", "");
        }

        if (imageSize != null && !imageSize.equals("Any")) {
            params += "&imgsz=" + imageSize.toLowerCase();
        }

        if (imageColor != null && !imageColor.equals("Any")) {
            params += "&imgcolor=" + imageColor.toLowerCase();
        }

        if (imageSite != null) {
            params += "&as_sitesearch=" + imageSite.toLowerCase();
        }

        return params;
    }
}
