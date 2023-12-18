

package io.github.krysiel86.processor;


import java.util.List;

import io.github.krysiel86.model.VASTCompanion;
import io.github.krysiel86.model.VASTMediaFile;


public interface VASTMediaPicker {

    VASTMediaFile pickVideo(List<VASTMediaFile> list);

    VASTCompanion pickImage(List<VASTCompanion> list);


}
