package com.icerock.yellowdoor.feature.personalInfo

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.flatMap
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize


class PersonalInfoScreen(
    private val theme: Theme,
    private val strings: Strings,
    private val styles: Styles,
    private val images: Images,
    private val createViewModelBlock: (
        EventsDispatcher<PersonalInfoViewModel.EventsListener>
    ) -> PersonalInfoViewModel,
    private val closeRoute: Route<Unit>,
    private val newsRoute: Route<Unit>,
    private val regionRoute: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem, PersonalInfoViewModel.EventsListener {

    private val viewModel: PersonalInfoViewModel = getViewModel {
        createViewModelBlock(createEventsDispatcher())
    }

    override val navigationBar: NavigationBar = NavigationBar.Normal(
        title = strings.title,
        styles = styles.navigationBar,
        backButton = NavigationBar.Normal.BarButton(
            icon = images.closeImage,
            action = {
                viewModel.didTapCloseButton()
            }
        )
    )

    override fun createContentWidget() = with(theme) {
        viewModel.eventsDispatcher.listen(this@PersonalInfoScreen, this@PersonalInfoScreen)

        constraint(size = WidgetSize.AsParent) {
            val scroll = +scroll(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                id = Id.Scroll,
                child = constraint(
                    size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.WrapContent)
                ) {
                    val avatarImage = +image(
                        size = WidgetSize.Const(SizeSpec.Exact(48.0f), SizeSpec.Exact(48.0f)),
                        image = viewModel.avatarImage,
                        id = Id.AvatarImage
                    )

                    val uploadPhotoButton = +button(
                        category = styles.uploadNewPhotoButton,
                        id = Id.UploadNewPhotoButton,
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                        content = ButtonWidget.Content.Text(Value.data(strings.uploadNewPhoto)),
                        onTap = viewModel::didTapUploadNewPhotoButton
                    )

                    val uploadPhotoButtonImage = +image(
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                        id = Id.RightArrowImage,
                        image = const(images.rightButtonArrow)
                    )

                    val birthdayField = +createField(
                        title = strings.birthday,
                        text = viewModel.birthday,
                        didTapBlock = viewModel::didTapRegion
                    )
                    
                    constraints {
                        avatarImage.topToTop(root.safeArea).offset(32)
                        avatarImage.leftToLeft(root.safeArea).offset(16)

                        uploadPhotoButtonImage.rightToRight(root.safeArea).offset(16)
                        uploadPhotoButtonImage.centerYToCenterY(avatarImage)

                        uploadPhotoButton.rightToLeft(uploadPhotoButtonImage).offset(16)
                        uploadPhotoButton.centerYToCenterY(uploadPhotoButtonImage)

                        birthdayField.leftToLeft(root.safeArea).offset(16)
                        birthdayField.topToBottom(avatarImage).offset(32)
                        birthdayField.rightToRight(root.safeArea).offset(16)

                    }
                })

            constraints {
                scroll.topToTop(root.safeArea)
                scroll.leftRightToLeftRight(root.safeArea)
                scroll.bottomToBottom(root.safeArea)
            }
        }
    }

    override fun routeToNews() {

    }

    override fun routeToRegionSelection() {
        println("1")
        regionRoute.route(arg = Unit)
    }

    private fun createField(
        title: StringDesc,
        text: LiveData<String>,
        didTapBlock: (() -> Unit)
    ): ConstraintWidget<WidgetSize.Const<SizeSpec.AsParent, SizeSpec.AsParent>> = with(theme) {
        return constraint(size = WidgetSize.AsParent) {
            val arrowImage = +image(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                id = Id.RightArrowImage,
                image = const(images.rightButtonArrow)
            )

            val titleText = +text(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                category = styles.selectableFieldTitle,
                text = const(title)
            )

            val contentText = +text(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                category = styles.selectableFieldContent,
                text = text.map { value: String ->
                    if (value.length == 0)
                        return@map strings.notIndicated

                    return@map value.desc()
                }
            )

            val button = +button(
                size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.Exact(38.0f)),
                content = ButtonWidget.Content.Text(Value.data("8ewr7cgt8fw3t87wt8v".desc())),
                onTap = {
                    println("0")
                    didTapBlock()
                }
            )

            constraints {
                arrowImage.rightToRight(root)
                arrowImage.centerYToCenterY(root)

                titleText.topToTop(root)
                titleText.leftToLeft(root)

                contentText.topToBottom(titleText).offset(4)
                contentText.leftToLeft(root)
                contentText.rightToLeft(arrowImage).offset(8)
                contentText.bottomToBottom(root)

                button.topToTop(root)
                button.leftRightToLeftRight(root)
                button.bottomToBottom(root)
            }
        }
    }

    class Styles(
        val navigationBar: NavigationBar.Normal.Styles,
        val uploadNewPhotoButton: ButtonWidget.Category,
        val selectableFieldTitle: TextWidget.Category,
        val selectableFieldContent: TextWidget.Category
    )

    interface Strings {
        val title: StringDesc
        val uploadNewPhoto: StringDesc
        val notIndicated: StringDesc
        val birthday: StringDesc
        val region: StringDesc
        val city: StringDesc
        val education: StringDesc
        val aboutMe: StringDesc
        val photo: StringDesc
        val video: StringDesc
        val save: StringDesc
    }

    interface Images {
        val avatarPlaceholderImage: Image
        val closeImage: ImageResource
        val rightButtonArrow: Image
    }

    object Id {
        object AvatarImage : ImageWidget.Id
        object UploadNewPhotoButton : ButtonWidget.Id
        object RightArrowImage : ImageWidget.Id
        object Scroll : ScrollWidget.Id
        object SelectableFieldTitle : TextWidget.Id
        object SelectableFieldContent : TextWidget.Id
    }
}