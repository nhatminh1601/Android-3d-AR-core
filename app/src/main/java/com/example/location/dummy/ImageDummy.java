package com.example.location.dummy;

import com.example.location.R;
import com.example.location.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageDummy {
    List<Image> imageList;

    public ImageDummy(int type) {
        if (type == 1) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Carnotaurus", "dinosaur/carnotaurus.sfb", "Hình khủng long", 1, R.drawable.d_carnotaurus, 1, "Khủng long chân thú (Theropoda) lớn sống ở Nam Mỹ vào cuối kỷ Creta, trong khoảng từ 72 tới 69,9 triệu năm trước"));
            imageList.add(new Image(2, "Ceratosaurus", "dinosaur/ceratosaurus.sfb", "Hình khủng long", 1, R.drawable.d_ceratosaurus, 1, "khủng long chân thú ăn thịt lớn sống vào kỷ Jura muộn (Kimmeridgia tới Tithonia), được phát hiện tại thành hệ Morrison ở Bắc Mỹ, và thành hệ Lourinhã ở Bồ Đào Nha.[1] Nó có hàm lớn, răng giống lưỡi dao, và một cái sừng lớn trên mõm. Chi trước khỏe nhưng khá ngắn."));
            imageList.add(new Image(3, "Cryolophosaurus", "dinosaur/cryolophosaurus.sfb", "Hình khủng long", 1, R.drawable.d_cryolophosaurus, 1,"Cryolophosaurus ellioti, sống vào thời kỳ đầu kỷ Jura ở nơi ngày nay là Nam Cực. Nó dài khoảng 6,5 mét (21 ft) và nặng 465 kilôgam (1.025 lb), nó là một trong những Therapoda lớn nhất đương thời. Vì mẫu vật duy nhất có vẻ chưa trưởng thành, cá thể Cryolophosaurus có lẽ còn lớn hơn."));
            imageList.add(new Image(4, "Diabloceratops", "dinosaur/diabloceratops.sfb", "Hình khủng long", 1, R.drawable.d_diabloceratops, 1,"khủng long tuyệt chủng thuộc họ Ceratopsidae sống cách ngày nay khoảng 79 triệu năm trong thời kỳ Creta muộn tại nơi ngày nay là Utah, Hoa Kỳ.[1] Diabloceratops có kích thước trung bình, ăn thực vật, đi bốn chân, và đạt chiều dài được ước tính là 5,5 m"));
            imageList.add(new Image(5, "Diplodocus", "dinosaur/diplodocus.sfb", "Hình khủng long", 1, R.drawable.d_diplodocus, 1,"Diplodocus (/dɪˈplɒdəkəs/,[1][2] /daɪˈplɒdəkəs/,[2] hay /ˌdɪploʊˈdoʊkəs/[1]) là một chi khủng long thuộc cận bộ Sauropoda và họ Diplodocidae, sống ở miền Tây Bắc Mỹ ngày nay vào cuối kỷ Jura. Trinidad Focus là một trong những hóa thạch khủng long phổ biến hơn được tìm thấy ở tầng giữa đến tầng trên hệ tầng Morrison, khoảng 154 đến 152 triệu năm trước, trong thời kỳ cuối Kimmeridgian."));
            imageList.add(new Image(6, "Gorgosaurus", "dinosaur/gorgosaurus.sfb", "Hình khủng long", 1, R.drawable.d_gorgosaurus, 1,"Gorgosaurus (/ˌɡɔːrɡ[invalid input: 'ɵ']ˈsɔːrəs/ GOR-go-SOR-əs) là một chi khủng long chân thú thuộc họ Tyrannosauridae sống vào thời kỳ Creta muộn tại nơi ngày nay là miền tây Bắc Mỹ, khoảng từ 76.6 tới 75.1 triệu năm trước. "));
            imageList.add(new Image(7, "Skeletondiplodocus", "dinosaur/skeletondiplodocus.sfb", "Hình khủng long", 1, R.drawable.f_stegosaurus, 1));
            imageList.add(new Image(8, "Skeletonteratophoneus", "dinosaur/skeletonteratophoneus.sfb", "Hình khủng long", 1, R.drawable.f_stegosaurus, 1));
            imageList.add(new Image(9, "Spinosaurus", "dinosaur/spinosaurus.sfb", "Hình khủng long", 1, R.drawable.d_spinosaurus, 1,"Spinosaurus (có nghĩa là \"thằn lằn gai\") là một chi khủng long ăn thịt sinh sống tại Bắc Phi, sống vào thời kỳ Alba và Cenoman của kỷ Phấn trắng, khoảng 112[1]-97[2] triệu năm trước. Chi này được biết tới lần đầu tiên từ hóa thạch ở Ai Cập năm 1912 và được nhà cổ sinh vật học người Đức Ernst Stromer miêu tả năm 1915. Bản hóa thạch gốc đã bị phá hủy vào chiến tranh thế giới thứ hai, nhưng tài liệu bổ sung đã được công bố những năm gần đây."));
            // cá
            imageList.add(new Image(10, "Angel 1", "fish/Angle1/AngelFish3.gltf", "Hình cá", 2, R.drawable.angel1, 1));
            imageList.add(new Image(11, "Angel Fish", "fish/AngelFish/AngelFish.gltf", "Hình cá", 2, R.drawable.angelfish, 1));
            imageList.add(new Image(12, "Angler", "fish/Angler/AngelerlFish.gltf", "Hình cá", 2, R.drawable.angler, 1,"Cá thiên thần lửa (Danh pháp khoa học: Centropyge loricula) là một loài cá trong họ Pomacanthidae. Tên tiếng Anh của nó là Flame Angelfish trong tiếng việt gọi là Thiên Thần Lửa. Chúng là một trong những dòng phổ biến nhất của cá thiên thần lùn (Dwarf angelfish)."));
            imageList.add(new Image(13, "Clown Fish", "fish/ClownFish/ClownFish.gltf", "Hình cá", 2, R.drawable.clown_fish, 1));
            imageList.add(new Image(14, "Fish", "fish/fish/fish.gltf", "Hình cá", 2, R.drawable.fish, 1));
            imageList.add(new Image(15, "Koi", "fish/Koi/Koifish.gltf", "Hình cá", 2, R.drawable.koi, 1,"Cá chép Koi (Nhật: 鯉 (Lý)/ こい Hepburn: Koi?, \"Cá chép\") hay cụ thể hơn Cá chép Nishikigoi (Nhật: 錦鯉 (Cẩm Lý)/ にしきこい Hepburn: Nishikikoi?, \"Cá chép thổ cẩm\") là một loại cá chép thường (Cyprinus carpio) đã được thuần hóa, lai tạo để nuôi làm cảnh trong những hồ nhỏ, được nuôi phổ biến tại Nhật Bản. Chúng có quan hệ họ hàng gần với cá vàng và, trên thực tế, kiểu cách nhân giống và nuôi cảnh là khá giống với cách nuôi cá vàng, có lẽ là do các cố gắng của những người nhân giống Nhật Bản trong việc ganh đua với cá vàng. Cá chép Koi và các hình xăm trên cá được người Nhật coi là điềm may mắn."));
            // chim
            imageList.add(new Image(16, "Canada", "bird/Canada/Canada.gltf", "Hình Chim", 3, R.drawable.chim, 1));
            imageList.add(new Image(17, "Flamingo", "bird/Flamingo/Flamingo.gltf", "Hình Chim", 3, R.drawable.chim, 1));
            imageList.add(new Image(18, "Seagull", "bird/Seagull/Seagull.gltf", "Hình Chim", 3, R.drawable.chim, 1));
            // thực vật
            imageList.add(new Image(19, "tree", "tree/tree/Tree02.gltf", "tree", 4, R.drawable.cay, 1));
            // xương
            imageList.add(new Image(20, "Bone", "bone/Bone/Bone01.gltf", "Bone", 5, R.drawable.xuong, 1));
            imageList.add(new Image(21, "Dinosaur", "bone/Dinosaur/DinosaurSkull.gltf", "Xương khủng long", 5, R.drawable.xuong, 1));
            imageList.add(new Image(22, "SprineBase", "bone/SprineBase/SprineBase.gltf", "SprineBase", 5, R.drawable.xuong, 1));
            imageList.add(new Image(23, "Trilobite", "bone/Trilobite/TrilobiteA.gltf", "TrilobiteA", 5, R.drawable.xuong, 1));
            imageList.add(new Image(24, "Trilobited", "bone/Trilobited/TrilobitedB.gltf", "TrilobitedB", 5, R.drawable.xuong, 1));
            // tranh
            imageList.add(new Image(25, "Van gogh", "tranh/vangoghmuseum/vangoghmuseum.gltf", "Tranh của Van gogh", 6, R.drawable.h1, 1,"Hoa hướng dương (tên gốc, trong tiếng Pháp: Tournesols) là tên của hai loại tranh về tĩnh vật được vẽ bởi họa sĩ người Hà Lan Vincent van Gogh. Loạt tranh đầu tiên, được thực hiện ở Paris năm 1887, mô tả những bông hoa nằm trên mặt đất, trong khi loạt tranh thứ hai, thực hiện một năm sau đó tại Arles, lại vẽ một bó hoa hướng dương đặt trong một chiếc bình."));
            imageList.add(new Image(26, "Van gogh", "tranh/b2/b2.gltf", "Tranh của Van gogh", 6, R.drawable.h4, 1));
            imageList.add(new Image(27, "Van gogh", "tranh/b3/b3.gltf", "Tranh của Van gogh", 6, R.drawable.h3, 1));
            imageList.add(new Image(28, "Van gogh", "tranh/b4/b4.gltf", "Tranh của Van gogh", 6, R.drawable.h2, 1));
        }


    }

    public List<Image> List() {
        return imageList;
    }
}
